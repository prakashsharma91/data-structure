package com.github.prakashsharma91.numeric;

import com.github.prakashsharma91.Fields;
import com.github.prakashsharma91.ReportTable;
import com.github.prakashsharma91.domain.Field;
import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;

import static com.github.prakashsharma91.Constants.DELTA;

public class ReportTableTest {

    @Benchmark
    @Test
    public void testParent() throws InstantiationException, IllegalAccessException {
        double val = 10;
        Amount amount1 = new Amount(val, val);
        Amount amount2 = new Amount(val, val);
        Amount amount3 = new Amount(val, val);
        Amount amount4 = new Amount(val, val);
        int parentRowId = 18, childRowId = 1, firstChildOfChild = 12, secondChildOfChild = 13;
        ReportTable<Integer, Field, Amount> report = new ReportTable<>(parentRowId);
        report.addRow(Fields.OPENING_CAPITAL, amount1, childRowId, firstChildOfChild);
        report.addRow(Fields.OPENING_CAPITAL, amount2, childRowId, secondChildOfChild);
        report.addRow(Fields.ENDING_CAPITAL, amount3, childRowId, firstChildOfChild);
        report.addRow(Fields.ENDING_CAPITAL, amount4, childRowId, secondChildOfChild);
        /**
         * 18 -> 1 -> 12 -> (10, 10)
         * 18 -> 1 -> 13 -> (10, 10)
         * 18 -> 20, 1 -> 20, 12 - 10, 13 - 10;
         */

        // parent values
        Assert.assertEquals(2*val, report.getColumnValue(Fields.OPENING_CAPITAL).getBase(), DELTA);
        Assert.assertEquals(2*val, report.getColumnValue(Fields.OPENING_CAPITAL).getLocal(), DELTA);

        // child values
        Assert.assertEquals(2*val, report.getColumnValue(Fields.OPENING_CAPITAL, childRowId).getBase(), DELTA);
        Assert.assertEquals(2*val, report.getColumnValue(Fields.OPENING_CAPITAL, childRowId).getLocal(), DELTA);

        // child's first child
        Assert.assertEquals(val, report.getColumnValue(Fields.OPENING_CAPITAL, childRowId, firstChildOfChild).getBase(), DELTA);
        Assert.assertEquals(val, report.getColumnValue(Fields.OPENING_CAPITAL, childRowId, firstChildOfChild).getLocal(), DELTA);

        // child's second child
        Assert.assertEquals(val, report.getColumnValue(Fields.OPENING_CAPITAL, childRowId, secondChildOfChild).getBase(), DELTA);
        Assert.assertEquals(val, report.getColumnValue(Fields.OPENING_CAPITAL, childRowId, secondChildOfChild).getLocal(), DELTA);

        // Second child

        amount1 = new Amount(val, val);
        amount2 = new Amount(val, val);
        amount3 = new Amount(val, val);
        amount4 = new Amount(val, val);
        childRowId = 2; firstChildOfChild = 22; secondChildOfChild = 23;
        report.addRow(Fields.OPENING_CAPITAL, amount1, childRowId, firstChildOfChild);
        report.addRow(Fields.OPENING_CAPITAL, amount2, childRowId, secondChildOfChild);
        report.addRow(Fields.ENDING_CAPITAL, amount3, childRowId, firstChildOfChild);
        report.addRow(Fields.ENDING_CAPITAL, amount4, childRowId, secondChildOfChild);

        /**
         * 18 -> 2 -> 12 -> (10, 10)
         * 18 -> 2 -> 13 -> (10, 10)
         * 18 -> 40, 1 -> 20, 12 - 10, 13 - 10, 2 -> 20, 22 - 10, 23 - 10;
         */

        // parent values
        Assert.assertEquals(4*val, report.getColumnValue(Fields.ENDING_CAPITAL).getBase(), DELTA);
        Assert.assertEquals(4*val, report.getColumnValue(Fields.ENDING_CAPITAL).getLocal(), DELTA);

        // child values
        Assert.assertEquals(2*val, report.getColumnValue(Fields.ENDING_CAPITAL, childRowId).getBase(), DELTA);
        Assert.assertEquals(2*val, report.getColumnValue(Fields.ENDING_CAPITAL, childRowId).getLocal(), DELTA);

        // child's first child
        Assert.assertEquals(val, report.getColumnValue(Fields.ENDING_CAPITAL, childRowId, firstChildOfChild).getBase(), DELTA);
        Assert.assertEquals(val, report.getColumnValue(Fields.ENDING_CAPITAL, childRowId, firstChildOfChild).getLocal(), DELTA);

        // child's second child
        Assert.assertEquals(val, report.getColumnValue(Fields.ENDING_CAPITAL, childRowId, secondChildOfChild).getBase(), DELTA);
        Assert.assertEquals(val, report.getColumnValue(Fields.ENDING_CAPITAL, childRowId, secondChildOfChild).getLocal(), DELTA);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
