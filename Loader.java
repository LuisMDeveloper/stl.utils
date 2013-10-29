
package stl;

import com.jme3.math.Vector3f;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LuisMDeveloper
 */
public class Loader {
    
    public ArrayList<Facet> facets;
    
    public Solid loadSTLFile(String filename){
        //Temporal Stage
        Vector3f normal = null;
        Vector3f[] outer = null;
        int vertexNo = 0;
        //Temporal Stage
        Solid solid = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            while((line = br.readLine()) != null) {
                String lineWords[] = line.split(" ");
                for (int i = 0; i < lineWords.length; i++) {
                    if("solid".equals(lineWords[i]) && (solid == null)){
                        solid = new Solid(lineWords[i+1]);
                        break;
                    }
                    if("facet".equals(lineWords[i]) && 
                            "normal".equals(lineWords[i+1]) && 
                            (normal == null)){
                        normal = parseVector(lineWords[i+2], lineWords[i+3], lineWords[i+4]);
                        //System.out.println("{"+lineWords[i]+" "+
                        //        lineWords[i+1]+" "+normal+"}");
                        break;
                    }else if("outer".equals(lineWords[i]) && 
                            "loop".equals(lineWords[i+1]) &&
                            (outer == null)){
                        outer = new Vector3f[3];
                        vertexNo = 0;
                        break;
                    }else if("vertex".equals(lineWords[i]) && (outer != null)){
                        outer[vertexNo] = parseVector(lineWords[i+1],lineWords[i+2], lineWords[i+3]);
                        vertexNo++;
                        break;
                    }else if("endloop".equals(lineWords[i]) && (outer != null)){
                        vertexNo = 0;
                        break;
                    }else if("endfacet".equals(lineWords[i]) && (outer != null)){
                        Facet facet = new Facet(normal, outer);
                        //System.out.println(facet);
                        solid.addFacet(facet);
                        normal = null;
                        outer = null;
                        break;
                    }else if("endsolid".equals(lineWords[i])){
                        
                        break;
                    }
                }
            }
            return solid;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return solid;
    }
    
    private ArrayList<Facet> loadAscii() {
        
        return null;
    }
    
    private Vector3f parseVector(String x, String y, String z){
        float xF = Float.valueOf(x);
        float yF = Float.valueOf(y);
        float zF = Float.valueOf(z);
        return new Vector3f(xF, yF, zF);
    }

}
