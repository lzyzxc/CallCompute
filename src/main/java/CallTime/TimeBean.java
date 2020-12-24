package CallTime;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TimeBean implements Writable {
    private Long time1;
    private Long time2;
    private Long time3;
    private Long time4;
    private Long time5;
    private Long time6;
    private Long time7;
    private Long time8;


    public TimeBean() {
    }

    public TimeBean(Long time1, Long time2, Long time3, Long time4, Long time5, Long time6, Long time7, Long time8) {
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
        this.time6 = time6;
        this.time7 = time7;
        this.time8 = time8;
    }

    public Long getTime1() {
        return time1;
    }

    public void setTime1(Long time1) {
        this.time1 = time1;
    }

    public Long getTime2() {
        return time2;
    }

    public void setTime2(Long time2) {
        this.time2 = time2;
    }

    public Long getTime3() {
        return time3;
    }

    public void setTime3(Long time3) {
        this.time3 = time3;
    }

    public Long getTime4() {
        return time4;
    }

    public void setTime4(Long time4) {
        this.time4 = time4;
    }

    public Long getTime5() {
        return time5;
    }

    public void setTime5(Long time5) {
        this.time5 = time5;
    }

    public Long getTime6() {
        return time6;
    }

    public void setTime6(Long time6) {
        this.time6 = time6;
    }

    public Long getTime7() {
        return time7;
    }

    public void setTime7(Long time7) {
        this.time7 = time7;
    }

    public Long getTime8() {
        return time8;
    }

    public void setTime8(Long time8) {
        this.time8 = time8;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(time1);
        dataOutput.writeLong(time2);
        dataOutput.writeLong(time3);
        dataOutput.writeLong(time4);
        dataOutput.writeLong(time5);
        dataOutput.writeLong(time6);
        dataOutput.writeLong(time7);
        dataOutput.writeLong(time8);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        time1 = dataInput.readLong();
        time2 = dataInput.readLong();
        time3 = dataInput.readLong();
        time4 = dataInput.readLong();
        time5 = dataInput.readLong();
        time6 = dataInput.readLong();
        time7 = dataInput.readLong();
        time8 = dataInput.readLong();
    }

    @Override
    public String toString() {
        return time1 + "\t" + time2 + "\t" + time3 + "\t" + time4 + "\t" + time5 + "\t" + time6 + "\t" + time7 + "\t" + time8;
    }
}
