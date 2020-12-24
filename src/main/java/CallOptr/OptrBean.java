package CallOptr;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OptrBean implements Writable {
    //电信
    private Long telecom;
    //移动
    private Long mobile;
    //联通
    private Long unicom;

    public OptrBean() {
    }

    public OptrBean(Long telecom, Long mobile, Long unicom) {
        this.telecom = telecom;
        this.mobile = mobile;
        this.unicom = unicom;
    }

    public Long getTelecom() {
        return telecom;
    }

    public void setTelecom(Long telecom) {
        this.telecom = telecom;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Long getUnicom() {
        return unicom;
    }

    public void setUnicom(Long unicom) {
        this.unicom = unicom;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(telecom);
        dataOutput.writeLong(mobile);
        dataOutput.writeLong(unicom);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        telecom = dataInput.readLong();
        mobile = dataInput.readLong();
        unicom = dataInput.readLong();
    }

    @Override
    public String toString() {
        return telecom + "\t" + mobile + "\t" + unicom;
    }
}
