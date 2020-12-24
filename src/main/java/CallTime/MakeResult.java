package CallTime;

import java.io.*;

public class MakeResult {
    //生成最终结果文件
    public void makeRes() throws IOException {
        String filePath = "out3.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader bufferedReader = new BufferedReader(reader);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("result3.txt"));

        String line = "";
        while((line = bufferedReader.readLine()) != null){
            String[] temp = line.split("\t");
            bufferedWriter.write(temp[0]+",");
            //统计所有通话时间
            long timeCount = 0;
            for (int i=1; i<temp.length; i++){
                timeCount += Long.parseLong(temp[i]);
            }
            if (timeCount != 0){
                for (int i=1; i<temp.length; i++){
                    double part = 100*(double)Long.parseLong(temp[i]) /(double) timeCount;
                    //整数比例
                    if (Math.round(part) - part==0){
                        bufferedWriter.write((long)part+"%,");
                    }
                    //小数比例
                    else {
                        bufferedWriter.write(String.format("%.2f", part)+"%,");
                    }
                }
            }
            else {
                for (int i=1; i<temp.length; i++){
                    bufferedWriter.write("0%,");
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
