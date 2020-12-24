package CallOptr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class CallOptr {
    public static class callMapper extends Mapper<Object, Text, Text, OptrBean> {
        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\t");
            String callType = fields[12];
            String callingOptr = fields[3];
            String calledOptr = fields[4];
            long telecom = 0;
            long mobile = 0;
            long unicom = 0;
            switch (callingOptr) {
                case "1":
                    telecom++;
                    break;
                case "2":
                    mobile++;
                    break;
                case "3":
                    unicom++;
                    break;
            }

            switch (calledOptr) {
                case "1":
                    telecom++;
                    break;
                case "2":
                    mobile++;
                    break;
                case "3":
                    unicom++;
                    break;
            }

            OptrBean optrBean = new OptrBean(telecom, mobile, unicom);
            context.write(new Text(callType), optrBean);
        }
    }

    public static class callReduce extends
            Reducer<Text, OptrBean, Text, OptrBean> {

        public void reduce(Text key, Iterable<OptrBean> values,
                           Context context)
                throws IOException, InterruptedException {
            long telecomSum = 0;
            long mobileSum = 0;
            long unicomSum = 0;

            for (OptrBean bean:values){
                telecomSum += bean.getTelecom();
                mobileSum += bean.getMobile();
                unicomSum += bean.getUnicom();
            }
            OptrBean sumBean = new OptrBean(telecomSum, mobileSum, unicomSum);
            context.write(key, sumBean);
        }
    }

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();

        String[] paths = new String[]{"/callcompute/input/tb_call_201202_random.txt","output2"};

        Job job = Job.getInstance(conf, "CallOptr");
        job.setPartitionerClass(TypePartitioner.class);
        job.setNumReduceTasks(4);

        job.setJarByClass(CallOptr.class);
        job.setMapperClass(CallOptr.callMapper.class);
        job.setReducerClass(CallOptr.callReduce.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(OptrBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(OptrBean.class);
        FileInputFormat.addInputPath(job, new Path(paths[0]));
        FileOutputFormat.setOutputPath(job, new Path(paths[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
