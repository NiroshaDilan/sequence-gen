package lk.coder.sequencegenerator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project sequence-generator
 * @Author DILAN on 2/5/2020
 */
@RestController
public class MyController {

    private DBConnection dbConnection;
    private SequenceGenerator sequenceGenerator;

    public MyController(DBConnection dbConnection, SequenceGenerator sequenceGenerator) {
        this.dbConnection = dbConnection;
        this.sequenceGenerator = sequenceGenerator;
    }

    @PostMapping("/create-table")
    public void createTable() {
        dbConnection.createTable();
    }

    @PostMapping("/insert")
    public Integer insertTable() {
        return dbConnection.insertTable();
    }

    @GetMapping("/nxt-seq")
    public Long getNextSeqNo() {
        return dbConnection.getNextSeqNo();
    }

    @GetMapping("/update-nxt/{seq}")
    public Integer updateNextSeq(@PathVariable Long seq) {
        return dbConnection.updateNextSeq(seq);
    }

    @GetMapping("/gen-seq")
    public Long getSeqNo() {
        return sequenceGenerator.getNext();
    }

    @GetMapping("/init-seq")
    public Long getInitSeqNo() {
        return dbConnection.getInitSeqNo();
    }
}
