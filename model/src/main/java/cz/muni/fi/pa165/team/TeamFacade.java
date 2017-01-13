package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.result.TeamResult;
import cz.muni.fi.pa165.team.match.TeamMatch;
import cz.muni.fi.pa165.team.match.TeamMatchGoalRepositoryImpl;
import cz.muni.fi.pa165.team.match.TeamMatchRepository;
import cz.muni.fi.pa165.team.match.result.MatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
@Service
@Transactional
public class TeamFacade
{

    private TeamService teamService;

    private TeamRepository teamRepository;

    private TeamMatchRepository teamMatchRepository;

    private TeamMatchGoalRepositoryImpl teamMatchGoalRepository;

    private EntityManager entityManager;

    @Autowired
    public TeamFacade(
        TeamService teamService,
        TeamRepository teamRepository,
        TeamMatchRepository teamMatchRepository,
        TeamMatchGoalRepositoryImpl teamMatchGoalRepository
    )
    {
        this.teamService = teamService;
        this.teamRepository = teamRepository;
        this.teamMatchRepository = teamMatchRepository;
        this.teamMatchGoalRepository = teamMatchGoalRepository;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    /**
     * Create and save the team.
     */
    public Team createTeam(String teamName)
    {
        Team sameNameTeam = teamRepository.findTeamByName(teamName);

        Team team = teamService.createTeam(
            teamName,
            sameNameTeam
        );

        entityManager.persist(team);
        entityManager.flush();

        return team;
    }

    /**
     * Change name of team.
     */
    public void changeTeamName(UUID teamId, String newTeamName)
    {
        Team team = teamRepository.getTeamById(teamId);
        Team sameTeamName = teamRepository.findTeamByName(newTeamName);

        teamService.changeTeamName(team, newTeamName, sameTeamName);

        entityManager.flush();
    }

    /**
     * Delete team.
     */
    public void deleteTeam(UUID teamId)
    {
        Team team = teamRepository.getTeamById(teamId);

        entityManager.createQuery("DELETE FROM TeamMatchGoal tmg WHERE tmg.match IN " +
            "(SELECT tm FROM TeamMatch tm WHERE tm.awayTeam = :tid OR tm.homeTeam = :tid)")
            .setParameter("tid", team)
            .executeUpdate();

        entityManager.createQuery("DELETE FROM TeamMatch tm WHERE tm.awayTeam = :tid OR " +
            "tm.homeTeam = :tid")
            .setParameter("tid", team)
            .executeUpdate();

        entityManager.createQuery("DELETE FROM TeamPlayer tp WHERE tp.team = :tid")
            .setParameter("tid", team)
            .executeUpdate();

        entityManager.remove(team);

        entityManager.flush();
    }

    /**
     * Get team statistics.
     */
    public TeamResult getTeamStatistics(UUID teamID)
    {
        int matchesPlayedCnt = 0;
        int winsCnt = 0;
        int lossesCnt = 0;
        int drawsCnt = 0;
        int goalsScoredCnt = 0;
        int goalsConcededCnt = 0;

        Team team = teamRepository.getTeamById(teamID);
        Collection<TeamMatch> playedMatches = teamMatchRepository.findAllPlayedMatchesOfTeam(teamID);
        if (playedMatches != null)
        {
            matchesPlayedCnt = playedMatches.size();
            for (TeamMatch match : playedMatches)
            {
                long matchGoalsScored = teamMatchGoalRepository.getGoalsCountByTeamInMatch(match.getId(), teamID);
                goalsScoredCnt += matchGoalsScored;

                Team opponent = (teamID == match.getHomeTeam().getId()) ? match.getAwayTeam() : match.getHomeTeam();
                long matchGoalsConceded = teamMatchGoalRepository.getGoalsCountByTeamInMatch(match.getId(), opponent.getId());
                goalsConcededCnt += matchGoalsConceded;

                if (matchGoalsScored == matchGoalsConceded) {
                    drawsCnt++;
                }
                if (matchGoalsScored > matchGoalsConceded) {
                    winsCnt++;
                }
                if (matchGoalsScored < matchGoalsConceded){
                    lossesCnt++;
                }
            }
        }

        return new TeamResult(team,
            matchesPlayedCnt,
            winsCnt,
            lossesCnt,
            drawsCnt,
            goalsScoredCnt,
            goalsConcededCnt
        );
    }

    /**
     * Get team statistics of all teams.
     */
    public Collection<TeamResult> getAllTeamStatistics()
    {
        Collection<TeamResult> teamsStatistics = new ArrayList<>();
        Collection<Team> teamsList = teamRepository.findAll();
        teamsStatistics.addAll(teamsList.stream().map(team -> getTeamStatistics(team.getId())).collect(Collectors.toList()));

        return teamsStatistics;
    }

    /**
     * Retrieves result for each played match.
     *
     * @return collection of all played matches results
     */
    public List<MatchResult> getPlayedTeamMatchesList(UUID teamID)
    {
        Collection<TeamMatch> playedMatches = teamMatchRepository.findAllPlayedMatchesOfTeam(teamID);
        List<MatchResult> matchResults = new ArrayList<>();

        for (TeamMatch pm : playedMatches)
        {
            MatchResult mr = new MatchResult(
                pm,
                teamMatchGoalRepository.getGoalsCountByTeamInMatch(pm.getId(), pm.getHomeTeam().getId()),
                teamMatchGoalRepository.getGoalsCountByTeamInMatch(pm.getId(), pm.getAwayTeam().getId())
            );

            matchResults.add(mr);
        }

        return matchResults;
    }
}
