/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

/**
 *
 * @author MY PC
 */
public class SystemManager {
    // Singleton instance
    private static SystemManager instance;
    
    // The single HR_System instance
    private final HR_System system;
    
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
}
