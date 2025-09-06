public class CricketWorldCupScore {
    private String team1;
    private String team2;
    private int runsTeam1;
    private int runsTeam2;

    public CricketWorldCupScore(String team1, String team2, int runsTeam1, int runsTeam2) {
        this.team1 = team1;
        this.team2 = team2;
        this.runsTeam1 = runsTeam1;
        this.runsTeam2 = runsTeam2;
    }

    public CricketWorldCupScore(String team1, String team2) {
        this(team1, team2, 0, 0);
    }

    public CricketWorldCupScore(String team1, int runsTeam1) {
        this(team1, "Opponent", runsTeam1, 0);
    }

    public CricketWorldCupScore(String team1, String team2, int runsTeam2) {
        this(team1, team2, 0, runsTeam2);
    }

    public void displayScore() {
        System.out.println("Cricket World Cup Score:");
        System.out.println(team1 + " vs " + team2);
        System.out.println("Runs by " + team1 + ": " + runsTeam1);
        System.out.println("Runs by " + team2 + ": " + runsTeam2);
        System.out.println("------------------------");
    }

    public static void main(String[] args) {
        CricketWorldCupScore match1 = new CricketWorldCupScore("India", "Australia", 250, 200);
        match1.displayScore();

        CricketWorldCupScore match2 = new CricketWorldCupScore("England", "South Africa");
        match2.displayScore();

        CricketWorldCupScore match3 = new CricketWorldCupScore("Pakistan", 180);
        match3.displayScore();

        CricketWorldCupScore match4 = new CricketWorldCupScore("Sri Lanka", "New Zealand", 220);
        match4.displayScore();
    }
}
