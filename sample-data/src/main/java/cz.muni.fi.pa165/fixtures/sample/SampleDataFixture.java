package cz.muni.fi.pa165.fixtures.sample;

import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamFacade;
import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.TeamPlayerFacade;
import cz.muni.fi.pa165.team.match.TeamMatch;
import cz.muni.fi.pa165.team.match.TeamMatchFacade;
import cz.muni.fi.pa165.team.match.TeamMatchGoal;
import cz.muni.fi.pa165.user.User;
import cz.muni.fi.pa165.user.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Component
public class SampleDataFixture
{

    private TeamFacade teamFacade;

    private TeamPlayerFacade teamPlayerFacade;

    private TeamMatchFacade teamMatchFacade;

    private UserFacade userFacade;

    @Autowired
    public SampleDataFixture(TeamFacade teamFacade, TeamPlayerFacade teamPlayerFacade, TeamMatchFacade teamMatchFacade, UserFacade userFacade)
    {
        this.teamFacade = teamFacade;
        this.teamPlayerFacade = teamPlayerFacade;
        this.teamMatchFacade = teamMatchFacade;
        this.userFacade = userFacade;
    }

    /**
     * Loads sample data.
     */
    public void loadData()
    {
        LocalDateTime today = LocalDateTime.now().withHour(12);
        LocalDateTime tomorrow = today.plusDays(1);

        Team team1 = teamFacade.createTeam("Ostrava FC");
        Team team2 = teamFacade.createTeam("Opava FC");
        Team team3 = teamFacade.createTeam("Praha FK");

        TeamPlayer player1 = teamPlayerFacade.createTeamPlayer("Lojza", "Mravenec", 180, 84, team1);
        TeamPlayer player2 = teamPlayerFacade.createTeamPlayer("Alex", "Boruvka", 181, 85, team1);
        TeamPlayer player3 = teamPlayerFacade.createTeamPlayer("John", "Doe", 185, 86, team1);

        TeamPlayer player4 = teamPlayerFacade.createTeamPlayer("Jarda", "Mravenec", 180, 84, team2);
        TeamPlayer player5 = teamPlayerFacade.createTeamPlayer("Bohumil", "Klika", 185, 87, team2);
        TeamPlayer player6 = teamPlayerFacade.createTeamPlayer("Marek", "Mord", 165, 95, team2);

        TeamPlayer player7 = teamPlayerFacade.createTeamPlayer("Pepa", "Lelek", 180, 84, team3);
        TeamPlayer player8 = teamPlayerFacade.createTeamPlayer("Pepa", "Prskavec", 189, 89, team3);
        TeamPlayer player9 = teamPlayerFacade.createTeamPlayer("Kamil", "Kordoba", 180, 84, team3);

        long time = System.currentTimeMillis();

        TeamMatch match1 = teamMatchFacade.createMatch(team1.getId(), team2.getId(), today, today.plusHours(1));
        TeamMatch match2 = teamMatchFacade.createMatch(team1.getId(), team3.getId(), today.plusMinutes(62), today.plusMinutes(62).plusHours(1));
        TeamMatch match3 = teamMatchFacade.createMatch(team2.getId(), team3.getId(), today.plusMinutes(200), today.plusMinutes(200).plusHours(1));

        TeamMatch match4 = teamMatchFacade.createMatch(team3.getId(), team2.getId(), tomorrow, null);
        TeamMatch match5 = teamMatchFacade.createMatch(team1.getId(), team3.getId(), tomorrow.plusMinutes(62), tomorrow.plusMinutes(62).plusHours(1));
        TeamMatch match6 = teamMatchFacade.createMatch(team2.getId(), team3.getId(), tomorrow.plusMinutes(200), tomorrow.plusMinutes(200).plusHours(1));

        TeamMatchGoal goal1 = teamMatchFacade.addNewScoredGoal(player1.getId(), player3.getId(), match1.getId(), team1.getId(), match1.getStartTime().plusMinutes(5));
        TeamMatchGoal goal2 = teamMatchFacade.addNewScoredGoal(player2.getId(), player1.getId(), match1.getId(), team1.getId(), match1.getStartTime().plusMinutes(11));
        TeamMatchGoal goal3 = teamMatchFacade.addNewScoredGoal(player5.getId(), player6.getId(), match1.getId(), team2.getId(), match1.getStartTime().plusMinutes(32));

        TeamMatchGoal goal4 = teamMatchFacade.addNewScoredGoal(player3.getId(), player2.getId(), match2.getId(), team1.getId(), match2.getStartTime().plusMinutes(6));
        TeamMatchGoal goal5 = teamMatchFacade.addNewScoredGoal(player7.getId(), player9.getId(), match2.getId(), team3.getId(), match2.getStartTime().plusMinutes(18));

        TeamMatchGoal goal6 = teamMatchFacade.addNewScoredGoal(player5.getId(), player6.getId(), match3.getId(), team2.getId(), match3.getStartTime().plusMinutes(1));
        TeamMatchGoal goal7 = teamMatchFacade.addNewScoredGoal(player8.getId(), player9.getId(), match3.getId(), team3.getId(), match3.getStartTime().plusMinutes(2));
        TeamMatchGoal goal8 = teamMatchFacade.addNewScoredGoal(player7.getId(), player9.getId(), match3.getId(), team3.getId(), match3.getStartTime().plusMinutes(3));
        TeamMatchGoal goal9 = teamMatchFacade.addNewScoredGoal(player8.getId(), player7.getId(), match3.getId(), team3.getId(), match3.getStartTime().plusMinutes(5));

        User admin = userFacade.createUser("admin@soccer.com", "admin");
        User moderator1 = userFacade.createUser("boruvka@boruvka.com", "heslo1");
        User moderator2 = userFacade.createUser("tresnicka@posta.cz", "heslo2");
        userFacade.createUser("labut123@letim.sk", "heslo3");
        userFacade.createUser("jablko@posta.cz", "heslo4");
        userFacade.createUser("jahoda@seznam.cz", "heslo5");
        userFacade.createUser("jezek@yahoo.com", "heslo6");

        userFacade.promoteUserToAdmin(admin.getId());
        userFacade.promoteUserToModerator(moderator1.getId());
        userFacade.promoteUserToModerator(moderator2.getId());
    }

}
