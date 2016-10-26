package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.UUID;
import javax.persistence.EntityManager;

/**
 *
 * @author Denis Galajda
 */
public interface TeamDao
{
    public Team findById(UUID id);
    public Team findTeamByName(String name);
    public Collection<Team> findAll();
    public void create(Team entity);
    public void update(Team entity);
    public void delete(Team entity);

}
