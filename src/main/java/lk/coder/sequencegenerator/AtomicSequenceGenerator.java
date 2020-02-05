package lk.coder.sequencegenerator;

import org.springframework.stereotype.Component;

/**
 * @Project sequence-generator
 * @Author DILAN on 2/5/2020
 */
@Component
public class AtomicSequenceGenerator implements SequenceGenerator {

    private DBConnection dbConnection;

    public AtomicSequenceGenerator(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public synchronized Long getNext() {
        Long nextSeqNo = dbConnection.getNextSeqNo();
        if (nextSeqNo != null) {
//            AtomicLong value = new AtomicLong(nextSeqNo);
            Long increment = ++nextSeqNo;
            if (nextSeqNo.toString().length() == 8) {
                increment = dbConnection.getInitSeqNo();
            }
            Integer integer = dbConnection.updateNextSeq(increment);
            if(integer == 1) {
                return increment;
            } else {
                System.out.println("next sequence isn't updated");
            }
        } else {
            System.out.println("Cannot get the next seq to increment");
        }
        return null;
    }
}
