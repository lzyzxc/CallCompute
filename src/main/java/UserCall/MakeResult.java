package UserCall;

import java.io.*;

public class MakeResult {
    //生成最终结果文件
    public void makeRes() throws IOException {
        String filePath = "out1.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader bufferedReader = new BufferedReader(reader);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("result1.txt"));

        String line = "";
        while((line = bufferedReader.readLine()) != null){
            String[] temp = line.split("\t");
            bufferedWriter.write(temp[0]+",");
            bufferedWriter.write(String.format("%.2f", (double)Long.parseLong(temp[1]) /29)+"\n");
        }
        bufferedReader.close();
        bufferedWriter.close();
    }

    public static void main(String[] args) throws IOException {
        MakeResult makeResult = new MakeResult();
        makeResult.makeRes();
    }
}
