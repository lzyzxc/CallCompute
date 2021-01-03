package CallOptr;

import java.io.*;

public class MakeResult {
    //生成最终结果文件
    public void makeRes() throws IOException {
        String filePath = "./result/out2.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader bufferedReader = new BufferedReader(reader);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./result/result2.txt"));

        String line = "";
        while((line = bufferedReader.readLine()) != null){
            String[] temp = line.split("\t");
            bufferedWriter.write(temp[0]+",");

            //统计所有通话
            long optrCount = 0;
            for (int i=1; i<temp.length; i++){
                optrCount += Long.parseLong(temp[i]);
            }
            for (int i=1; i<temp.length; i++) {
                bufferedWriter.write(String.format("%.2f", 100 * (double) Long.parseLong(temp[i]) / (double) optrCount) + "%");
                if(i < temp.length -1 ){
                    bufferedWriter.write(",");
                }
            }
            bufferedWriter.write("\n");
        }
        bufferedReader.close();
        bufferedWriter.close();
    }

    public static void main(String[] args) throws IOException {
        MakeResult makeResult = new MakeResult();
        makeResult.makeRes();
    }
}
