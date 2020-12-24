package CallTime;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallTime {
    private static DateFormat df = new SimpleDateFormat("HH:mm:ss");
    private static Date[] dates;

    static {
        try {
            dates = new Date[]{df.parse("00:00:00"), df.parse("03:00:00"), df.parse("06:00:00"), df.parse("09:00:00"),
                    df.parse("12:00:00"), df.parse("15:00:00"), df.parse("18:00:00"), df.parse("21:00:00"), df.parse("24:00:00")};
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static class callMapper extends Mapper<Object, Text, Text, TimeBean> {
        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\t");
            String callingNbr = fields[1];
            String start = fields[9];
            long rawDur = Integer.parseInt(fields[11]);

            Long[] times = new Long[8];
            for (int i=0; i<8; i++){
                times[i] = 0L;
            }

            Date startTime = new Date();
            Date endTime = new Date();
            try {
                startTime = df.parse(start);
                endTime = new Date(startTime.getTime() + rawDur*1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (int i=0; i<dates.length-1; i++){
                if (startTime.after(dates[i]) && startTime.before(dates[i+1])){
                    if (endTime.before(dates[i+1])){
                        times[i] += rawDur;
                    }
                    else {
                        times[i] += (dates[i+1].getTime() - startTime.getTime())/1000;
                        times[(i+1)%8] += (endTime.getTime() - dates[i+1].getTime())/1000;
                    }
                    break;
                }
            }

            TimeBean timeBean = new TimeBean(times[0], times[1], times[2], times[3], times[4], times[5], times[6], times[7]);
            context.write(new Text(callingNbr), timeBean);
        }
    }

    public static class callReduce extends
            Reducer<Text, TimeBean, Text, TimeBean> {

        public void reduce(Text key, Iterable<TimeBean> values,
                           Context context)
                throws IOException, InterruptedException {
            Long[] times = new Long[8];
            for (int i=0; i<8; i++){
                times[i] = 0L;
            }

            for (TimeBean bean:values){
                times[0] += bean.getTime1();
                times[1] += bean.getTime2();
                times[2] += bean.getTime3();
                times[3] += bean.getTime4();
                times[4] += bean.getTime5();
                times[5] += bean.getTime6();
                times[6] += bean.getTime7();
                times[7] += bean.getTime8();
            }
            TimeBean sumBean = new TimeBean(times[0], times[1], times[2], times[3], times[4], times[5], times[6], times[7]);
            context.write(key, sumBean);
        }
    }

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();

        String[] paths = new String[]{"/callcompute/input/tb_call_201202_random.txt","output3"};

        Job job = Job.getInstance(conf, "CallTime");

        job.setJarByClass(CallTime.class);
        job.setMapperClass(CallTime.callMapper.class);
        job.setReducerClass(CallTime.callReduce.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TimeBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(TimeBean.class);
        FileInputFormat.addInputPath(job, new Path(paths[0]));
        FileOutputFormat.setOutputPath(job, new Path(paths[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
