---
layout: page
title: System architecture
---

![architecture]({{ site.baseurl }}/images/architecture.png)

## Entity

* zapouzdřuje data
* zodpovídá za svoji konzistenci, validuje si přijímaná data, není anémická
	* v každém momentu své existence je validní
	* [AnemicDomainModel by Martin Fowler](http://www.martinfowler.com/bliki/AnemicDomainModel.html)
	* [Anemic domain model on Wikipedia](https://en.wikipedia.org/wiki/Anemic_domain_model)
	* Hlavní důvod proč nechceme takové entity je, že anémický model popírá principy OOP, viz zkrácené "Liabilities" na wikipedii.
	* Nemá zbytečné settery, místo toho pokud je potřeba měnit její stav, má metody které nejprve validují, že taková změna je možná.
	* Validuje pouze věci, které je možné ověřit bez sahání do databáze nebo někam "mimo vzduchem" - tedy například nemůže ověřit, že neexistuje jiný uživatel se stejným emailem.
	* Je nezbytné, aby byla konzistentní už v momentě vytvoření
* nemá autogenerované `id` hibernatem
	* Má [UUID](https://en.wikipedia.org/wiki/Globally_unique_identifier), které získá už v konstruktoru => není závislá na přiřazení idčka od hibernate.
	* Pokud bych chtěl implementovat `equals()` a `hashCode()`, použiji k tomu pouze field `id` (UUID plněné v konstruktoru)
* je persistence agnostic/ignorant
	* [Nejstravitelnější zdůvodnění je na SO](http://stackoverflow.com/a/906094/602899)
* její identita je daná její existencí
	* [Classification by Eric Evans on Martin Fowler's blog](http://martinfowler.com/bliki/EvansClassification.html)
	* Tak jako v reálném světe jsem já jako jedinec uníkátní z principu toho že existuji, tak stejnou vlastnost má i entita a nějaký identifikátor je pouze implementační detail srovnatelný s mým rodným číslem. Ovšem budu existovat i bez rodného čísla a nikdy nebudu stejný jako někdo jiný.
	* Entity nikdy nebudeme porovnávat přes bussines equivalence, ale pouze přes reference (fakt že ukazují na stejné místo v paměti).
	* Mohlo by se zdát, že dvě entity jsou stejné pokud mají stejné `id` a typ, ale to není tak jednoduché. Dva objekty jsou stejné pouze pokud mají stejnou identitu a v případě že je načítáme z databáze tak budou stejné pouze v případě, že mají stejné nejen `id` a typ ale navíc je vrátila stejná instance EntityManageru, která by měla po dobu běhu transakce být neměnná.

## Services

* neví o databázi
	* díky tomu budou jakékoliv testy služeb velice rychlé
	* pokud k otestování služby potřebuji databázi, není to unit test ale integrační test
	* trully unit testy by to samozřejmě byly pouze v případě, pokud bychom poctivě mockovali všechny závislosti třídy - což dělat nebudeme z pohodlnosti, ovšem pokud by nastala situace, že se to někde hodí, tak můžeme
* může používat jiné služby
* může vytvářet entity, takové entity pak vrací jako výsledek svého volání
* může vracet entity s informací, že se mají smazat
* může kontrolovat oprávnění
* kontroluje pouze věci, které neumí zkontrolovat sama entita
	* například že uživatel v databázi je unikátní, ovšem na databázi nesahá, místo toho si nechá předat nullable argumentem případného již existujícího uživatele se stejným emailem od fasády
* nesmí sahat na fasády ani repozitáře
* není nutné, aby odpovídaly 1:1 entitám, repozitářům nebo fasádám
* je striktně bezstavová
* další výhody:
	* maximální znovupoužitelnost i v rámci jiných services

## Repositories

* obsahují pouze `find*` a `get*` metody, které pouze čtou
	* pokud je metoda `find*`, může vracet null a nikdy neháže výjimky pokud výsledek nenajde
	* pokud je metoda `get*`, nesmí vracet null a pokud výsledek nenajde, tak háže výjimku
* striktní zákaz volání jakéhokoliv `persist()`, `flush()`, `clear()`, `merge()` a dalších metod na modifikaci entit nebo čištění identity mapy
* nikdy nepřijímá v argumentech entity, ani když podle nich potřebuje hledat
	* místo toho přijímá vždy jejich identifikátory - častěji mám k dispozici `id` entity, než entitu samotnou
* není nutné, aby odpovídaly 1:1 entitám, službám nebo fasádám

## Facades

* veřejné rozhranní aplikace
	* metody odpovídají jednotlivým use-cases, které je v aplikaci možné provádět
	* slouží jako znovupoužitelná horní vrstva abstrakce nad modelem - je možné použít v controlleru, cli rozhranní, restovém api, cronech, ...
* obsahuje co nejmenší množství logiky, v ideálním případě vůbec žádnou
* využívají služby, repozitáře a entity manager
* z repozitářů načítá data, které vyžaduje služba ke svojí práci
* řídí persistenci entit
	* pokud služba kterou fasáda volá vytvoří novou entitu, fasáda ji zapersistuje
	* pokud služba kterou fasáda volá vrátí entitu s tím že má být smazána, fasáda ji smaže
* pokud operace samotná nevyžaduje logiku (kontrolu oprávnění, validace, ...) není nutné volat služby
	* například mazání většího množství entit je lepší udělat jedním `DELETE ...` HQL dotazem na úrovni fasády 
* přijímá buď surová data, nebo struktury (v třídách, které jsou definované ve stejném namespace)
* může vracet buď specifické result objeky, nebo entity
	* entity zodpovídají za svůj stav - nevadí tedy, že je vypustíme mimo fasády
* není nutné, aby odpovídaly 1:1 entitám, službám nebo repozitářům

## Controllers

* zodpovídá za namapování HTTP requestu na struktury, se kterými pracuje fasáda
* pokud pouze čte nějaká data a není nutné ověřovat oprávnění, nevadí příme použití repozitáře
* zodpovídá za reprezentaci odpobědi
	* v případě použití šablony nevadí když entity pošleme do šablony
	* v případě rest api využívají specifické `*ResponseFactory` třídy, které zodpovídají za mapování výsledku volání fasády (buď result objekt nebo entity) na struktury vhodné pro prezentaci ven (např json)

## Společné

* striktní dodržování Inversion of Control
	* [Dependency injection on wikipedia](https://en.wikipedia.org/wiki/Dependency_injection)
	* [Inversion of Control Containers and the Dependency Injection pattern by Martin Fowler](http://www.martinfowler.com/articles/injection.html)
	* Pokud třída nejde vytvořit bez DI Containeru, je špatně napsaná
		* inject nad private property/metody je anti-pattern
		* inject do public property/metody/konstruktoru je správně
