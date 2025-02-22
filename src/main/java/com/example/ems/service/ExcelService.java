
package com.example.ems.service;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import java.io.InputStreamReader;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.ems.dto.UserExcelDto;

public class ExcelService {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    
    
    
    /**
     * @param file
     * @return
     */
    public static boolean hasSupportedFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType) || "text/csv".equals(contentType);
    }


    
    
    
    /**
     * @param is
     * @return
     */
    public static List<UserExcelDto> excelToUsers(InputStream is) {
        List<UserExcelDto> users = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0); 
            Iterator<Row> rows = sheet.iterator();

            boolean firstRow = true; 
            while (rows.hasNext()) {
                Row row = rows.next();
                if (firstRow) {
                    firstRow = false;
                    continue; 
                }
                
                             
                UserExcelDto user = UserExcelDto.builder()
                        .name(row.getCell(0).getStringCellValue())
                        .email(row.getCell(1).getStringCellValue())
                        .password(row.getCell(2).getStringCellValue())
                        .roleId((int) row.getCell(3).getNumericCellValue()) 
                        .build();


                users.add(user);
            }

            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }

        return users;
    }
    
    
    
    
    
    public static List<UserExcelDto> csvToUsers(InputStream is) {
        List<UserExcelDto> users = new ArrayList<>();
        
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(is))) {
            String[] values;
            boolean firstRow = true;
            
            while ((values = csvReader.readNext()) != null) {
                if (firstRow) { 
                    firstRow = false; 
                    continue; 
                }

                UserExcelDto user = UserExcelDto.builder()
                        .name(values[0])
                        .email(values[1])
                        .password(values[2])
                        .roleId(Integer.parseInt(values[3])) // Convert to integer
                        .build();
                
                users.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }

        return users;
    }
    
}