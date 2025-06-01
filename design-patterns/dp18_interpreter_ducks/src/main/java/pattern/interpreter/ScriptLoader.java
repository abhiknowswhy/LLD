package pattern.interpreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ScriptLoader {
    
    public static String loadScriptFromResources(String resourcePath) {
        StringBuilder script = new StringBuilder();
        
        try (InputStream inputStream = ScriptLoader.class.getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
            
        } catch (IOException e) {
            System.err.println("Error loading script: " + e.getMessage());
            return null;
        }
        
        return script.toString();
    }
    
    public static String loadScriptFromFile(String filePath) {
        StringBuilder script = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error loading script file: " + e.getMessage());
            return null;
        }
        
        return script.toString();
    }
}
