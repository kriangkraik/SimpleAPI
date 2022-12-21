package tmith.pdffile.apppdf.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import tmith.pdffile.apppdf.Models.PdfData;

@RestController
public class CreatePDFController {
        public static String namestr;
        public static String datestr;
        

        /**
         * @return
         * @throws MalformedURLException
         * @throws IOException
         */
        @PostMapping("/createPDF")
        public ResponseEntity<Map<String, String>> createPDF(@RequestBody PdfData pdfdata) {
                namestr = pdfdata.getName();
                datestr = pdfdata.getDate();

                System.out.println(pdfdata.getName());
                System.out.println(pdfdata.getDate());

                try {
                        createFile();
                        Map<String, String> response = new HashMap<>();
                        response.put("statuscode", "200");
                        response.put("message", "create PDF successfully");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                } catch (Exception e) {
                        Map<String, String> response = new HashMap<>();
                        response.put("statuscode", "500");
                        response.put("message", "Cannot createPDF No successfully!");
                        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        /**
         * 
         * @return
         * @throws MalformedURLException
         * @throws IOException
         * @throws DocumentException
         */
        public static PdfPTable GetHeader() throws MalformedURLException, IOException, DocumentException {
                // Bold Font Thaisarabun
                BaseFont bf_bold = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew Bold.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font h1 = new Font(bf_bold, 18);
                Font bold = new Font(bf_bold, 16);
                Font smallBold = new Font(bf_bold, 14);

                PdfPTable headerTable = new PdfPTable(2);
                headerTable.setTotalWidth(530f);
                headerTable.setHorizontalAlignment(0);
                headerTable.setSpacingAfter(20);
                // headerTable.DefaultCell.Border = Rectangle.NO_BORDER;

                float[] headerTableColWidth = new float[2];
                headerTableColWidth[0] = 220f;
                headerTableColWidth[1] = 310f;

                headerTable.setWidths(headerTableColWidth);
                headerTable.setLockedWidth(true);
                Image png = Image.getInstance("D://SrcPDF//image//krut.png");
                png.scaleAbsolute(40, 40);

                PdfPCell headerTableCell_0 = new PdfPCell(png);
                headerTableCell_0.setHorizontalAlignment(Element.ALIGN_LEFT);
                headerTableCell_0.setBorder(Rectangle.NO_BORDER);
                headerTable.addCell(headerTableCell_0);

                PdfPCell headerTableCell_1 = new PdfPCell(new Phrase("บันทึกข้อความ", h1));
                headerTableCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
                headerTableCell_1.setVerticalAlignment(Element.ALIGN_BOTTOM);
                headerTableCell_1.setBorder(Rectangle.NO_BORDER);
                headerTable.addCell(headerTableCell_1);
                return headerTable;
        }

        /**
         * 
         * @return
         * @throws DocumentException
         * @throws IOException
         */
        public static PdfPTable GetHeaderDetail() throws DocumentException, IOException {
                // Bold Font Thaisarabun
                BaseFont bf_bold = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew Bold.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font h1 = new Font(bf_bold, 18);
                Font bold = new Font(bf_bold, 16);
                Font smallBold = new Font(bf_bold, 14);

                // Font Thaisarabun Normal
                BaseFont bf_normal = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font normal = new Font(bf_normal, 16);
                Font small = new Font(bf_normal, 14);

                PdfPTable table = new PdfPTable(2);
                table.setTotalWidth(530f);
                table.setHorizontalAlignment(0);
                table.setSpacingAfter(10);

                float[] tableWidths = new float[2];
                tableWidths[0] = 400f;
                tableWidths[1] = 130f;

                table.setWidths(tableWidths);
                table.setLockedWidth(true);

                Chunk blank = new Chunk(" ", normal);
                Phrase p = new Phrase();

                p.add(new Chunk("ส่วนราชการ", bold));
                p.add(new Chunk(blank));
                p.add(new Chunk("วิเทศสัมพันธ์", normal));

                PdfPCell cell0 = new PdfPCell(p);
                cell0.setBorder(Rectangle.NO_BORDER);

                table.addCell(cell0);

                p = new Phrase();
                p.add(new Chunk("โทร", bold));
                p.add(new Chunk(blank));
                p.add(new Chunk("๐๒-๒๕๗-๘๙๖๘", normal));

                PdfPCell cell1 = new PdfPCell(p);
                cell1.setBorder(Rectangle.NO_BORDER);

                table.addCell(cell1);

                p = new Phrase();
                p.add(new Chunk("ที่", bold));
                p.add(new Chunk(blank));

                // p.add(new Chunk("บมจ. คุ้มภัยโตเกียวมารีนประกันภัย", normal));
                p.add(new Chunk(namestr, normal));

                cell0 = new PdfPCell(p);
                cell0.setBorder(Rectangle.NO_BORDER);

                table.addCell(cell0);

                p = new Phrase();
                p.add(new Chunk("วันที่", bold));
                p.add(new Chunk(blank));
                p.add(new Chunk(datestr, normal));

                cell1 = new PdfPCell(p);
                cell1.setBorder(Rectangle.NO_BORDER);

                table.addCell(cell1);

                p = new Phrase();
                p.add(new Chunk("เรื่อง", bold));
                p.add(new Chunk(blank));
                p.add(new Chunk("ขออนุมัติเดินทางไปต่างประเทศ", normal));

                cell0 = new PdfPCell(p);
                cell0.setBorder(Rectangle.NO_BORDER);
                cell0.setColspan(2);
                table.addCell(cell0);
                return table;
        }

        /**
         * 
         * @return
         * @throws DocumentException
         * @throws IOException
         */
        public static Paragraph GetBodyHeader() throws DocumentException, IOException {
                // Bold Font Thaisarabun
                BaseFont bf_bold = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew Bold.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font h1 = new Font(bf_bold, 18);
                Font bold = new Font(bf_bold, 16);
                Font smallBold = new Font(bf_bold, 14);

                // Font Thaisarabun Normal
                BaseFont bf_normal = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font normal = new Font(bf_normal, 16);
                Font small = new Font(bf_normal, 14);

                Phrase p = new Phrase();
                p.add(new Chunk("เรียน ", normal));
                p.add(new Chunk("รองอธิการบดีฝ่ายองค์กรสัมพันธ์และสารสนเทศ", normal));

                Paragraph para = new Paragraph(p);
                para.setSpacingBefore(20);
                para.setSpacingAfter(20);
                return para;
        }

        /**
         * 
         * @return
         * @throws DocumentException
         * @throws IOException
         */
        public static Paragraph GetBody() throws DocumentException, IOException {
                // Bold Font Thaisarabun
                BaseFont bf_bold = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew Bold.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font h1 = new Font(bf_bold, 18);
                Font bold = new Font(bf_bold, 16);
                Font smallBold = new Font(bf_bold, 14);

                // Font Thaisarabun Normal
                BaseFont bf_normal = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font normal = new Font(bf_normal, 16);
                Font small = new Font(bf_normal, 14);

                Paragraph para = new Paragraph();
                para.setFirstLineIndent(38.1f);

                para.add(new Phrase("ด้วย", normal));
                para.add(new Phrase("งานวิเทศสัมพันธ์", normal));
                para.add(new Phrase("ขออนุมัติให้นักศึกษาจำนวน" + "1" + " คน เดินทางไปราชการต่างประเทศระหว่างวันที่"
                                + "master.StartDate" + " ถึงวันที่ " + "master.EndDate" + " รวม " + "master.PeriodDay"
                                + " วัน เพื่อดำเนินกิจกรรมดังต่อไปนี้", normal));
                return para;
        }

        /**
         * 
         * @return
         * @throws DocumentException
         * @throws IOException
         */
        public static PdfPTable GetActivities() throws DocumentException, IOException {
                // Bold Font Thaisarabun
                BaseFont bf_bold = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew Bold.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font h1 = new Font(bf_bold, 18);
                Font bold = new Font(bf_bold, 16);
                Font smallBold = new Font(bf_bold, 14);

                // Font Thaisarabun Normal
                BaseFont bf_normal = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font normal = new Font(bf_normal, 16);
                Font small = new Font(bf_normal, 14);

                PdfPTable table = new PdfPTable(3);
                table.setTotalWidth(530f);
                table.setHorizontalAlignment(0);
                table.setSpacingBefore(20);
                table.setSpacingAfter(20);

                // ชื่อกิจกรรม
                // สถาบัน
                // ประเทศ

                float[] columnWidths = new float[3];
                columnWidths[0] = 200f;
                columnWidths[1] = 200f;
                columnWidths[2] = 130f;

                table.setWidths(columnWidths);
                table.setLockedWidth(true);

                PdfPCell cell0 = new PdfPCell(new Phrase("กิจกรรม", bold));
                cell0.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell0.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell0);

                PdfPCell cell1 = new PdfPCell(new Phrase("สภานที่", bold));
                cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell1.setBorder(Rectangle.NO_BORDER);

                table.addCell(cell1);

                PdfPCell cell2 = new PdfPCell(new Phrase("ประเทศ", bold));
                cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell2.setBorder(Rectangle.NO_BORDER);

                table.addCell(cell2);

                /*
                 * List<MasterActivity> activity = master.GetActivities();
                 * 
                 * foreach (MasterActivity a in activity)
                 * {
                 * cell0 = new PdfPCell(new Phrase(a.ActivityNameThai, normal));
                 * cell0.HorizontalAlignment = Element.ALIGN_LEFT;
                 * cell0.Border = Rectangle.NO_BORDER;
                 * table.AddCell(cell0);
                 * 
                 * cell1 = new PdfPCell(new Phrase(a.HostName, normal));
                 * cell1.HorizontalAlignment = Element.ALIGN_LEFT;
                 * cell1.Border = Rectangle.NO_BORDER;
                 * table.AddCell(cell1);
                 * 
                 * Institution host = Institution.GetById(a.HostId);
                 * 
                 * cell2 = new PdfPCell(new Phrase(host.CountryName, normal));
                 * cell2.HorizontalAlignment = Element.ALIGN_LEFT;
                 * cell2.Border = Rectangle.NO_BORDER;
                 * table.AddCell(cell2);
                 * }
                 */

                return table;
        }

        /**
         * 
         * @return
         * @throws DocumentException
         * @throws IOException
         */
        public static Paragraph GetBodyFooter() throws DocumentException, IOException {
                // Bold Font Thaisarabun
                BaseFont bf_bold = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew Bold.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font h1 = new Font(bf_bold, 18);
                Font bold = new Font(bf_bold, 16);
                Font smallBold = new Font(bf_bold, 14);

                // Font Thaisarabun Normal
                BaseFont bf_normal = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font normal = new Font(bf_normal, 16);
                Font small = new Font(bf_normal, 14);

                Paragraph para = new Paragraph(
                                new Phrase("จึงเรียนมาเพื่อโปรดพิจารณาอนุมัติด้วย จักเป็นพระคุณยิ่ง", normal));
                para.setFirstLineIndent(38.1f);
                para.setSpacingAfter(25);
                return para;
        }

        /**
         * 
         * @param pdfDoc
         * @throws DocumentException
         * @throws IOException
         */
        public static void GetSignature(Document pdfDoc) throws DocumentException, IOException {
                // Bold Font Thaisarabun
                BaseFont bf_bold = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew Bold.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font h1 = new Font(bf_bold, 18);
                Font bold = new Font(bf_bold, 16);
                Font smallBold = new Font(bf_bold, 14);

                // Font Thaisarabun Normal
                BaseFont bf_normal = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font normal = new Font(bf_normal, 16);
                Font small = new Font(bf_normal, 14);

                Paragraph para;
                Phrase p;
                Chunk dotLine = new Chunk("……………………………………………", normal);

                // if (master.LevelId.Equals(“D”))
                // {
                // p = new Phrase(dotLine);
                // p.Add(new Chunk(“หัวหน้าภาควิชา”, normal));
                // para = new Paragraph(p);
                // pdfDoc.Add(para);
                // }

                p = new Phrase(dotLine);
                p.add(new Chunk("หัวหน้าภาควิชา", normal));
                para = new Paragraph(p);
                para.setSpacingAfter(15);

                pdfDoc.add(para);

                p = new Phrase(dotLine);
                p.add(new Chunk("คณบดี", normal));
                para = new Paragraph(p);
                para.setSpacingAfter(15);
                pdfDoc.add(para);
        }

        /**
         * 
         * @return
         * @throws DocumentException
         * @throws IOException
         */
        public static PdfPTable GetStudentList() throws DocumentException, IOException {
                // Bold Font Thaisarabun
                BaseFont bf_bold = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew Bold.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font h1 = new Font(bf_bold, 18);
                Font bold = new Font(bf_bold, 16);
                Font smallBold = new Font(bf_bold, 14);

                // Font Thaisarabun Normal

                BaseFont bf_normal = BaseFont.createFont("D://SrcPDF//fonts//THSarabunNew.ttf",
                                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font normal = new Font(bf_normal, 16);
                Font smallNormal = new Font(bf_normal, 14);

                Phrase p;
                PdfPTable table = new PdfPTable(8);
                table.setTotalWidth(530f);
                table.setHorizontalAlignment(0);
                // table.SpacingAfter = 20;
                // headerTable.DefaultCell.Border = Rectangle.NO_BORDER;

                float[] colWidths = new float[8];
                colWidths[0] = 30f;
                colWidths[1] = 70f;
                colWidths[2] = 70f;
                colWidths[3] = 70f;
                colWidths[4] = 70f;
                colWidths[5] = 70f;
                colWidths[6] = 70f;
                colWidths[7] = 70f;

                table.setWidths(colWidths);
                table.setLockedWidth(true);

                PdfPCell cell;

                p = new Phrase("รายชื่อผู้เดินทางจาก " + "๒๐ ธันวาคม ๒๕๖๕" + " ถึง " + "๒๐ ธันวาคม ๒๕๖๐", normal);

                cell = new PdfPCell(p);
                cell.setColspan(8);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPaddingBottom(10);

                table.addCell(cell);

                // region Header

                cell = new PdfPCell(new Phrase("ที่", smallBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell);

                cell = new PdfPCell(new Phrase("รหัสนักศึกษา", smallBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell);

                cell = new PdfPCell(new Phrase("คำนำหน้า", smallBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell);

                cell = new PdfPCell(new Phrase("ชื่อ", smallBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("สกุล", smallBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("คณะ", smallBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("วันที่เริ่ม", smallBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("วันที่สิ้นสุด", smallBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                // endregion

                // region Data
                List<String> listname = new ArrayList<String>();
                listname.add("เกรียงไกร เกษกุล");
                listname.add("ทดสอบ ทดสอบ");
                listname.add("ทดสอบ ทดสอบ");
                listname.add("ทดสอบ ทดสอบ");
                listname.add("ทดสอบ ทดสอบ");
                listname.add("ทดสอบ ทดสอบ");

                int i = 0;
                for (String str : listname) {
                        String s = String.valueOf(i + 1);
                        cell = new PdfPCell(new Phrase(s, smallNormal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(str, smallNormal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("นาย", smallNormal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("str", smallNormal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("str", smallNormal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("str", smallNormal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("str", smallNormal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("str", smallNormal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        i += 1;
                }

                /*
                 * List<OutboundApplication> apps = master.GetApplications();
                 * 
                 * int i = 0;
                 * 
                 * foreach (OutboundApplication app in apps)
                 * {
                 * cell = new PdfPCell(new Phrase((i + 1).ToString(), smallNormal));
                 * cell.HorizontalAlignment = Element.ALIGN_RIGHT;
                 * table.AddCell(cell);
                 * 
                 * cell = new PdfPCell(new Phrase(app.StudentId, smallNormal));
                 * cell.HorizontalAlignment = Element.ALIGN_CENTER;
                 * table.AddCell(cell);
                 * 
                 * cell = new PdfPCell(new Phrase(app.TitleName, smallNormal));
                 * cell.HorizontalAlignment = Element.ALIGN_LEFT;
                 * table.AddCell(cell);
                 * 
                 * cell = new PdfPCell(new Phrase(app.Firstname, smallNormal));
                 * cell.HorizontalAlignment = Element.ALIGN_LEFT;
                 * table.AddCell(cell);
                 * 
                 * cell = new PdfPCell(new Phrase(app.Lastname, smallNormal));
                 * cell.HorizontalAlignment = Element.ALIGN_LEFT;
                 * table.AddCell(cell);
                 * 
                 * cell = new PdfPCell(new Phrase(app.FacultyNameThai, smallNormal));
                 * cell.HorizontalAlignment = Element.ALIGN_LEFT;
                 * table.AddCell(cell);
                 * 
                 * cell = new PdfPCell(new Phrase(app.StartDate, smallNormal));
                 * cell.HorizontalAlignment = Element.ALIGN_CENTER;
                 * table.AddCell(cell);
                 * 
                 * cell = new PdfPCell(new Phrase(app.EndDate, smallNormal));
                 * cell.HorizontalAlignment = Element.ALIGN_CENTER;
                 * table.AddCell(cell);
                 * 
                 * i += 1;
                 * }
                 * 
                 * //endregion
                 */

                return table;
        }

        public void createFile() {
                // เริ่มต้นสร้างเอกสาร PDF
                /**
                 * @Parameter PageSizeA4
                 * @Parameter Margin Left,Right,Top,Botton
                 */
                Document pdfdocument = new Document(PageSize.A4, 30, 30, 20, 20);
                try {
                        PdfWriter pdfWriter = PdfWriter.getInstance(pdfdocument,
                                        new FileOutputStream("D://ExportFile//หนังสือบันทึกข้อความ.pdf"));
                        pdfdocument.open();
                        pdfdocument.add(GetHeader());
                        pdfdocument.add(GetHeaderDetail());
                        LineSeparator line = new LineSeparator();
                        pdfdocument.add(line);
                        pdfdocument.add(GetBodyHeader());
                        pdfdocument.add(GetBody());
                        pdfdocument.add(GetActivities());
                        pdfdocument.add(GetBodyFooter());
                        GetSignature(pdfdocument);
                        pdfdocument.newPage();
                        pdfdocument.add(GetStudentList());

                        pdfWriter.setCloseStream(true);
                        pdfdocument.close();
                        System.out.println("Creaet File PDF Complete!");
                } catch (DocumentException | IOException e) {
                        e.printStackTrace();
                } finally {
                        pdfdocument.close();
                }
        }

}
