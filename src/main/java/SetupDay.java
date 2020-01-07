import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupDay {
    public static void main(String[] args) throws Exception {
        String day = "15";
        String year = "2019";

        String folder = "src/main/kotlin/y"+year+"/d"+day+"/";
        String testFolder = "src/test/kotlin/y"+year+"/d"+day+"/";
        String resourcesFilePrefix = "src/main/resources/y"+year+"d"+day;
        String base = "src/main/resources/Base.kt";
        String baseTest = "src/test/resources/BaseTest.kt";

        new File(folder).mkdirs();
        new File(testFolder).mkdirs();

        Map y = new HashMap();
        y.put("cookie", readLine("src/main/resources/secrets.properties"));

        String baseKotlin = readLine(base).replaceAll("Base", "Day"+day);
        String baseKotlinTest = readLine(baseTest).replaceAll("Base", "Day"+day);
        writeToFile(folder + "Day"+day+".kt",baseKotlin);
        writeToFile(testFolder + "Day"+day+"Test.kt", baseKotlinTest);

        List<String> puzzleInput = get("https://adventofcode.com/"+year+"/day/"+ Integer.parseInt(day) + "/input", y);
        writeToFile(resourcesFilePrefix+ ".txt", String.join("\n", puzzleInput));
    }

    static List<String> get(String urlToScrape, Map<String,String> headers) throws Exception {
        List<String> lines = new ArrayList<>();
        URL url = new URL(urlToScrape);
        URLConnection uc = url.openConnection();
        for (Map.Entry<String,String> header : headers.entrySet()) {
            uc.setRequestProperty(header.getKey(), header.getValue());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return lines;
    }

    static String readLine(String textdoc) throws Exception {
        BufferedReader input = new BufferedReader(new FileReader(textdoc));
        String toReturn = "";
        String line;
        while((line = input.readLine()) != null){
            toReturn+=line+"\n";
        }
        return toReturn.substring(0, toReturn.length()-1);
    }

    static void writeToFile(String file, String toWrite) throws Exception {
        FileWriter outFile = new FileWriter(file);
        PrintWriter out = new PrintWriter(outFile);
        out.println(toWrite);
        out.close();
    }
}
