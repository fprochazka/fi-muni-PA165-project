package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
public interface TeamPlayerDao {

    public TeamPlayer findById(UUID id);
    public Collection<TeamPlayer> findPlayerByFirstname(String firstname);
    public Collection<TeamPlayer> findPlayerBySurname(String surname);
    public Collection<TeamPlayer> findPlayerByAge(int age);
    public Collection<TeamPlayer> findPlayerByTeam(Team team);
    public Collection<TeamPlayer> findPlayerByHeight(int height);
    public Collection<TeamPlayer> findPlayerByWeight(int weight);
    public void create(TeamPlayer entity);
    public void update(TeamPlayer entity);
    public void delete(TeamPlayer entity);

}
