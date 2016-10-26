package com.example.excelrw;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.example.employees.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

import org.apache.poi.ss.usermodel.CellType;

/**
 * Created by User on 022 22.10.16.
 */
public class ReaderExcelFile {

    private InputStream inputStream;
    private int readType;
    public static final int READ_AS_SUBJECT = 0;
    public static final int READ_AS_TEACHER = 1;
    TeacherService teacherService = new TeacherService();

    public ReaderExcelFile(InputStream inputStream,int readType)
    {
        this.inputStream = inputStream;
        this.readType = readType;
    }

    public void read() throws Exception {
        switch (readType){
            case 0 :
                readAsSubject();
                break;
            case 1 :
                readAsTeacher();
                break;
        }
    }

    private void readAsSubject() throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        //System.out.println("Number please... :" + firstSheet.getLastRowNum());
        //DataFormatter formatter = new DataFormatter();

        for( int i = 1; i < firstSheet.getLastRowNum(); i++)
        {
            Row nextRow = firstSheet.getRow(i);
            //System.out.print(nextRow.getCell(0)+" ");
            String subjectName = "";
            if(nextRow.getFirstCellNum() == 0)
            {
                subjectName = nextRow.getCell(0).toString();
            }
            //System.out.print(nextRow.getCell(1)+" ");
            int hours = Double.valueOf(nextRow.getCell(1).toString()).intValue();
            //System.out.print(nextRow.getCell(2));
            Teacher teacher = teacherService.findTeacherByName(nextRow.getCell(2).toString());

            SubjectList.getInstance().add(new Subject(subjectName,hours,teacher));

            System.out.println();
        }

        /*while (i<firstSheet.getLastRowNum()) {
            Row nextRow = firstSheet.getRow(i++);
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0 :
                            System.out.print(cell.getStringCellValue()+" ");

                            cell = cellIterator.next();
                            cell.getNumericCellValue();
                            System.out.print(cell.getNumericCellValue()+" ");
                            break;
                        case 1 :
                            System.out.print("-||-"+" ");
                            System.out.print(cell.getNumericCellValue()+" ");
                            break;
                        }
                        cell = cellIterator.next();
                        cell.getStringCellValue();
                        System.out.print(cell.getStringCellValue()+" ");
                    }
            System.out.println();
        }*/

        workbook.close();
        inputStream.close();

    }

    private void readAsTeacher() throws Exception {

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(4);
        System.out.println("Number please... : "+ firstSheet.getLastRowNum());
        //DataFormatter formatter = new DataFormatter();

        for( int i = 1; i < firstSheet.getLastRowNum(); i++)
        {

            Row nextRow = firstSheet.getRow(i);

            System.out.print(nextRow.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)+" ");
            String fullName = nextRow.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            System.out.print(nextRow.getCell(2)+" ");
            String state = nextRow.getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            String rate = state.subSequence(state.indexOf("(") + 1,state.indexOf(")")).toString();
            System.out.print(rate);
            TeacherList.getInstance().add(new Teacher(fullName,Double.valueOf(rate)));

            System.out.println();
        }


        workbook.close();
        inputStream.close();

    }
}

