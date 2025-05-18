package Logic;

public class SystemManager {
    // Singleton instance
    private static SystemManager instance;

    // The single HR_System instance (remove 'final' to allow setting)
    private HR_System system;

    // Private constructor
    private SystemManager() {
        system = new HR_System();
    }

    // Get the singleton instance
    public static synchronized SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }
        return instance;
    }

    // Get the HR_System instance
    public HR_System getSystem() {
        return system;
    }

    // Set the HR_System instance (add this method)
    public void setSystem(HR_System system) {
        this.system = system;
    }
}
