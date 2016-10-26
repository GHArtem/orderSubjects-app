package com.example.excelrw;

import com.example.employees.Subject;
import com.example.employees.SubjectList;
import com.example.employees.SubjectService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

/**
 * Created by User on 022 22.10.16.
 */
public class WriterExcelFile {

    public WriterExcelFile() {}

    public static  void write() throws IOException {
        Workbook wb = new XSSFWorkbook();
        CreationHelper factory = wb.getCreationHelper();
        CellStyle cellStyle = wb.createCellStyle();
        CellStyle cellStyle1 = wb.createCellStyle();
        Font font = wb.createFont();
        Font font1 = wb.createFont();
        Cell cell;
        font1.setFontHeightInPoints((short)10);
        cellStyle.setFont(font1);

        File file = new File("F:\\testFile.xlsx");
        FileOutputStream fileOut = new FileOutputStream(file);
        Sheet summary = wb.createSheet("Summary");
        Sheet discipline = wb.createSheet("Дисциплины");
        Sheet сourseDegree = wb.createSheet("Курсовые и дипломные");
        Sheet students = wb.createSheet("Студенты 2016-2017");
        Sheet teachers = wb.createSheet("Преподаватели 2016-2017");
        Sheet oadMOU = wb.createSheet("Нагрузка МОУ");

        List<Subject> subjectList = SubjectList.getInstance();
        Row firstRow = summary.createRow(0);
        firstRow.setHeightInPoints(new Double(59.25).floatValue());
        //firstRow.setRowStyle(cellStyle);

        cell =  firstRow.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Название дисциплины");

        cell =  firstRow.createCell(1);
        cellStyle1.setRotation(new Double(90).shortValue());
        font.setFontHeightInPoints(new Double(9).shortValue());
        cellStyle1.setFont(font);
        cell.setCellStyle(cellStyle1);
        cell.setCellValue("Кол-во часов");


        cell =  firstRow.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Закрепленный преподаватель");

        for(int i = 0; i<subjectList.size();i++)
        {
            Row newRow = summary.createRow(i+1);
            //newRow.setRowStyle(cellStyle);
            Subject subject = subjectList.get(i);

            cell =  newRow.createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(subject.getSubjectName());

            cell =  newRow.createCell(1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(subject.getHours());

            cell =  newRow.createCell(2);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(subject.getTeacher().getFullName());

        }
        summary.autoSizeColumn(0);
        summary.autoSizeColumn(1);
        summary.autoSizeColumn(2);
        wb.write(fileOut);
        fileOut.close();

    }
}
