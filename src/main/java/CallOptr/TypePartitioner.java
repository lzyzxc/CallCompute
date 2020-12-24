package CallOptr;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

public class TypePartitioner extends Partitioner<Text, OptrBean> {
    public static HashMap<String, Integer> typeDict = new HashMap<>();
    static {
        typeDict.put("1",1);
        typeDict.put("2",2);
        typeDict.put("3",3);
    }

    @Override
    public int getPartition(Text key, OptrBean callOptr, int i) {
        Integer type = typeDict.get(key.toString());

        return type == null?0:type;
    }
}
