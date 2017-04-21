package soselab.mpg.testreader.controller;

import soselab.mpg.testreader.controller.dto.UATDTO;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bernie on 2017/4/21.
 */
public class UATDTOAndRunNumber {
    private final int runNumber;
    private final List<UATDTO> uatdtos;
    private byte[] content;

    public UATDTOAndRunNumber(int runNumber, List<UATDTO> uatdtos, byte[] content) {
        this.runNumber = runNumber;
        this.uatdtos = uatdtos;
        this.content = content;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public List<UATDTO> getUatdtos() {
        return uatdtos;
    }

    @Override
    public String toString() {
        return "UATDTOAndRunNumber{" +
                "runNumber=" + runNumber +
                ", uatdtos=" + uatdtos +
                ", content=" + Arrays.toString(content) +
                '}';
    }

    public byte[] getContent() {
        return content;
    }

}
