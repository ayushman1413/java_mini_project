public class Membership {
    private static int nextMembershipId = 1;

    public static synchronized int generateMembershipId() {
        return nextMembershipId++;
    }
}
