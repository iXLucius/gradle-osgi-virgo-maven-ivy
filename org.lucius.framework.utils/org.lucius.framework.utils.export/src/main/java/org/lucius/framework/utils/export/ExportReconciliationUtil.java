package org.lucius.framework.utils.export;

import java.awt.Color;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.slf4j.LoggerFactory;

/**
 * 导出对账单工具类
 * @author wanghongye
 * @Date:2016-8-10
 */
public class ExportReconciliationUtil
{
    
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExportReconciliationUtil.class);
    
    /**
     * 导对账单详情
     */
    @SuppressWarnings("deprecation")
    public static void exportDetail(OutputStream outputStream, Reconciliation reconciliation,
            List<ReconciliationDetail> reconciliationDetailList) throws Exception
    {
        // 创建一个Workbook
        WritableWorkbook book = Workbook.createWorkbook(outputStream);

        // 该Excel中使用第一种字体：宋体，14号， 加粗
        WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), 14,
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);

        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        // 单元格中的内容水平方向居左
        titleFormat.setAlignment(jxl.format.Alignment.CENTRE);

        titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 标题的颜色，RGB值分别为：165、165、165
        Color color1 = Color.decode("#A5A5A5");

        book.setColourRGB(Colour.ORANGE, color1.getRed(), color1.getGreen(), color1.getBlue());

        Colour colour1 = Colour.ORANGE;

        titleFormat.setBackground(colour1);

        WritableSheet sheet = book.createSheet("对账单详情", 0);

        // 设置列宽
        sheet.setColumnView(0, 20);
        sheet.setColumnView(1, 40);
        sheet.setColumnView(2, 20);
        sheet.setColumnView(3, 20);
        sheet.setColumnView(4, 20);
        sheet.setColumnView(5, 30);
        sheet.setColumnView(6, 30);
        sheet.setColumnView(7, 35);

        // 第1行行高
        sheet.setRowView(0, 500);

        // 设置类型ws.addCell(new jxl.write.Label(列, 行, 内容.));
        sheet.addCell(new Label(0, 0, "对账单信息", titleFormat));
        sheet.addCell(new Label(1, 0, ""));
        sheet.addCell(new Label(2, 0, ""));
        sheet.addCell(new Label(3, 0, ""));
        sheet.addCell(new Label(4, 0, ""));
        sheet.addCell(new Label(5, 0, ""));
        sheet.addCell(new Label(6, 0, ""));
        sheet.addCell(new Label(7, 0, ""));

        // 合并单元格
        sheet.mergeCells(0, 0, 7, 0);

        // 第2行行高
        sheet.setRowView(1, 500);

        // 设置标题文字
        sheet.addCell(new Label(0, 1, "对账开始时间", titleFormat));
        sheet.addCell(new Label(1, 1, "对账截止时间", titleFormat));
        sheet.addCell(new Label(2, 1, "对账支付方", titleFormat));
        sheet.addCell(new Label(3, 1, "对账总金额", titleFormat));
        sheet.addCell(new Label(4, 1, "订单对账金额", titleFormat));
        sheet.addCell(new Label(5, 1, "订单归属平台分成金额", titleFormat));
        sheet.addCell(new Label(6, 1, "会员缴费金额", titleFormat));
        sheet.addCell(new Label(7, 1, "会员缴费归属平台分成金额", titleFormat));

        // 该Excel中使用第二种字体：Arial，12号
        WritableFont subTitleFont = new WritableFont(WritableFont.createFont("Arial"), 12);

        WritableCellFormat subTitleFormat = new WritableCellFormat(subTitleFont);

        // 单元格中的内容水平方向居左
        subTitleFormat.setAlignment(jxl.format.Alignment.CENTRE);

        subTitleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 数据的颜色，RGB值分别为：242、242、242
        Color color2 = Color.decode("#F2F2F2");

        book.setColourRGB(Colour.DARK_TEAL, color2.getRed(), color2.getGreen(), color2.getBlue());

        Colour colour2 = Colour.DARK_TEAL;

        subTitleFormat.setBackground(colour2);

        // 第3行行高
        sheet.setRowView(2, 400);

        sheet.addCell(new Label(0, 2, reconciliation.getReconciliationStartTime().toLocaleString(),
                subTitleFormat));
        sheet.addCell(new Label(1, 2, reconciliation.getReconciliationEndTime().toLocaleString(),
                subTitleFormat));
        sheet.addCell(new Label(2, 2, reconciliation.getReconciliationShopName(), subTitleFormat));
        sheet.addCell(new Label(3, 2, reconciliation.getTotalAmount().toString(), subTitleFormat));
        sheet.addCell(new Label(4, 2, reconciliation.getOrderAmount().toString(), subTitleFormat));
        sheet.addCell(new Label(5, 2, reconciliation.getReconciliationOrderAmount().toString(),
                subTitleFormat));
        sheet.addCell(new Label(6, 2, reconciliation.getVipAmount().toString(), subTitleFormat));
        sheet.addCell(new Label(7, 2, reconciliation.getReconciliationVIPAmount().toString(),
                subTitleFormat));

        // 第4行行高
        sheet.setRowView(3, 400);

        // 该Excel中使用第三种字体：宋体，14号， 加粗
        WritableFont textFont = new WritableFont(WritableFont.createFont("宋体"), 14,
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);

        WritableCellFormat textFormat = new WritableCellFormat(textFont);

        // 单元格中的内容水平方向居左
        textFormat.setAlignment(jxl.format.Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 表头的颜色，RGB值分别为：216、216、216
        Color color3 = Color.decode("#D8D8D8");

        book.setColourRGB(Colour.SEA_GREEN, color3.getRed(), color3.getGreen(), color3.getBlue());

        Colour colour3 = Colour.SEA_GREEN;

        textFormat.setBackground(colour3);
        sheet.setRowView(3, 500);

        sheet.addCell(new Label(0, 3, "交易类型", textFormat));
        sheet.addCell(new Label(1, 3, "订单号", textFormat));
        sheet.addCell(new Label(2, 3, "交易时间", textFormat));
        sheet.addCell(new Label(3, 3, "交易商品名称", textFormat));
        sheet.addCell(new Label(4, 3, "交易商品数量", textFormat));
        sheet.addCell(new Label(5, 3, "交易金额", textFormat));
        sheet.addCell(new Label(6, 3, "平台分成比率（%）", textFormat));
        sheet.addCell(new Label(7, 3, "平台分成金额", textFormat));

        // 该Excel中使用第四种字体：Arial, 12号， 无背景色
        WritableFont subTextFont = new WritableFont(WritableFont.createFont("Arial"), 12);

        WritableCellFormat subTextFormat = new WritableCellFormat(subTextFont);

        // 单元格中的内容水平方居中
        subTextFormat.setAlignment(jxl.format.Alignment.CENTRE);

        subTextFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 该Excel中使用第五种字体：宋体, 12号， 无背景色
        WritableFont bold5 = new WritableFont(WritableFont.createFont("宋体"), 12);

        WritableCellFormat wcfFormat5 = new WritableCellFormat(bold5);

        // 单元格中的内容水平方向居左
        wcfFormat5.setAlignment(jxl.format.Alignment.CENTRE);

        wcfFormat5.setBorder(Border.ALL, BorderLineStyle.THIN);

        if (null != reconciliationDetailList)
        {
            for (int i = 0; i < reconciliationDetailList.size(); ++i)
            {
                ReconciliationDetail reconciliationDetail = reconciliationDetailList.get(i);

                int row = i + 4;

                sheet.setRowView(row, 400);

                sheet.addCell(new Label(0, row, reconciliationDetail.getDealType() == 1 ? "商品销售"
                        : reconciliationDetail.getDealType() == 2 ? "书店会员费" : "退货", subTextFormat));
                sheet.addCell(new Label(1, row, reconciliationDetail.getOrderCode(), subTextFormat));
                sheet.addCell(new Label(2, row,
                        reconciliationDetail.getDealDate().toLocaleString(), subTextFormat));
                sheet.addCell(new Label(3, row, reconciliationDetail.getGoodsName(), subTextFormat));
                sheet.addCell(new Label(4, row, reconciliationDetail.getGoodsCount() + "",
                        subTextFormat));
                sheet.addCell(new Label(5, row, reconciliationDetail.getDealMoney().toString(),
                        subTextFormat));
                sheet.addCell(new Label(6, row, reconciliationDetail.getPlatformPercent()
                        .toString(), subTextFormat));
                sheet.addCell(new Label(7, row,
                        reconciliationDetail.getPlatformAmount().toString(), subTextFormat));

            }
        }

        book.write();
        book.close();
    }

    /**
     * 自主结算导出文档
     * @param outputStream
     * @param reconciliation
     * @param orders
     * @throws Exception
     */
    public static void exportPublisherReconciliationDetail(OutputStream outputStream,
            Reconciliation reconciliation, List<Order> orders) throws Exception{
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
        // 创建一个Workbook
        WritableWorkbook book = Workbook.createWorkbook(outputStream);

        // 该Excel中使用第一种字体：宋体，14号， 加粗
        WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), 14,
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);

        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        // 单元格中的内容水平方向居左
        titleFormat.setAlignment(jxl.format.Alignment.CENTRE);

        titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 标题的颜色，RGB值分别为：165、165、165
        Color color1 = Color.decode("#A5A5A5");

        book.setColourRGB(Colour.ORANGE, color1.getRed(), color1.getGreen(), color1.getBlue());

        Colour colour1 = Colour.ORANGE;

        titleFormat.setBackground(colour1);

        WritableSheet sheet = book.createSheet("对账单详情", 0);

        // 设置列宽
        sheet.setColumnView(0, 30);
        sheet.setColumnView(1, 25);
        sheet.setColumnView(2, 20);
        sheet.setColumnView(3, 20);
        sheet.setColumnView(4, 20);
        sheet.setColumnView(5, 25);
        sheet.setColumnView(6, 20);
        sheet.setColumnView(7, 22);
        sheet.setColumnView(8, 22);
        sheet.setColumnView(9, 17);

        // 第1行行高
        sheet.setRowView(0, 500);

        // 设置类型ws.addCell(new jxl.write.Label(列, 行, 内容.));
        sheet.addCell(new Label(0, 0, "对账单信息", titleFormat));
        sheet.addCell(new Label(1, 0, ""));
        sheet.addCell(new Label(2, 0, ""));
        sheet.addCell(new Label(3, 0, ""));
        sheet.addCell(new Label(4, 0, ""));
        sheet.addCell(new Label(5, 0, ""));
        sheet.addCell(new Label(6, 0, ""));
        sheet.addCell(new Label(7, 0, ""));
        sheet.addCell(new Label(8, 0, ""));
        sheet.addCell(new Label(9, 0, ""));
        
        // 合并单元格
        sheet.mergeCells(0, 0, 9, 0);

        // 第2行行高
        sheet.setRowView(1, 500);

        // 设置标题文字
        sheet.addCell(new Label(0, 1, "对账日期", titleFormat));
        sheet.addCell(new Label(1, 1, "", titleFormat));
        sheet.addCell(new Label(2, 1, "", titleFormat));
        sheet.addCell(new Label(3, 1, "累计交易金额", titleFormat));
        sheet.addCell(new Label(4, 1, "", titleFormat));
        sheet.addCell(new Label(5, 1, "", titleFormat));
        sheet.addCell(new Label(6, 1, "累计收入", titleFormat));
        sheet.addCell(new Label(7, 1, "", titleFormat));
        sheet.addCell(new Label(8, 1, "累计单数", titleFormat));
        sheet.addCell(new Label(9, 1, "", titleFormat));

        // 合并单元格
        sheet.mergeCells(0, 1, 2, 1);
        sheet.mergeCells(3, 1, 5, 1);
        sheet.mergeCells(6, 1, 7, 1);
        sheet.mergeCells(8, 1, 9, 1);
        
        // 该Excel中使用第二种字体：Arial，12号
        WritableFont subTitleFont = new WritableFont(WritableFont.createFont("Arial"), 12);
        
        WritableCellFormat subTitleFormat = new WritableCellFormat(subTitleFont);
        
        

        // 单元格中的内容水平方向居左
        subTitleFormat.setAlignment(jxl.format.Alignment.CENTRE);

        subTitleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        

        // 数据的颜色，RGB值分别为：242、242、242
        Color color2 = Color.decode("#F2F2F2");

        book.setColourRGB(Colour.DARK_TEAL, color2.getRed(), color2.getGreen(), color2.getBlue());

        Colour colour2 = Colour.DARK_TEAL;

        subTitleFormat.setBackground(colour2);

        // 第3行行高
        sheet.setRowView(2, 400);

        sheet.addCell(new Label(0, 2, simpleDF.format(reconciliation.getReconciliationStartTime()) + "-" + simpleDF.format(reconciliation.getReconciliationEndTime()), subTitleFormat));
        sheet.addCell(new Label(1, 2, "", subTitleFormat));
        sheet.addCell(new Label(2, 2, "", subTitleFormat));
        sheet.addCell(new jxl.write.Number(3, 2, reconciliation.getTotalAmount().doubleValue(), handNumberFormat(reconciliation.getTotalAmount())));
        sheet.addCell(new Label(4, 2, "", subTitleFormat));
        sheet.addCell(new Label(5, 2, "", subTitleFormat));
        sheet.addCell(new jxl.write.Number(6, 2, reconciliation.getPublisherAmount(), handNumberFormat(reconciliation.getPublisherAmount())));
        sheet.addCell(new Label(7, 2, "", subTitleFormat));
        sheet.addCell(new Label(8, 2, reconciliation.getOrderCount() + "", subTitleFormat));
        sheet.addCell(new Label(9, 2, "", subTitleFormat));
        // 合并单元格
        sheet.mergeCells(0, 2, 2, 2);
        sheet.mergeCells(3, 2, 5, 2);
        sheet.mergeCells(6, 2, 7, 2);
        sheet.mergeCells(8, 2, 9, 2);
        
        // 第4行行高
        sheet.setRowView(3, 400);

        // 该Excel中使用第三种字体：宋体，14号， 加粗
        WritableFont textFont = new WritableFont(WritableFont.createFont("宋体"), 14,
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);

        WritableCellFormat textFormat = new WritableCellFormat(textFont);

        // 单元格中的内容水平方向居左
        textFormat.setAlignment(jxl.format.Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 表头的颜色，RGB值分别为：216、216、216
        Color color3 = Color.decode("#D8D8D8");

        book.setColourRGB(Colour.SEA_GREEN, color3.getRed(), color3.getGreen(), color3.getBlue());

        Colour colour3 = Colour.SEA_GREEN;

        textFormat.setBackground(colour3);
        sheet.setRowView(3, 500);
        
        sheet.addCell(new Label(0, 3, "订单编号", textFormat));
        sheet.addCell(new Label(1, 3, "交易时间", textFormat));
        sheet.addCell(new Label(2, 3, "支付来源", textFormat));
        sheet.addCell(new Label(3, 3, "商品编号", textFormat));
        sheet.addCell(new Label(4, 3, "交易商品", textFormat));
        sheet.addCell(new Label(5, 3, "交易数量（个）", textFormat));
        sheet.addCell(new Label(6, 3, "我的收入（元）", textFormat));
        sheet.addCell(new Label(7, 3, "单价（元）", textFormat));
        sheet.addCell(new Label(8, 3, "商品总价（元）", textFormat));
        sheet.addCell(new Label(9, 3, "分成比（%）", textFormat));
        
        // 该Excel中使用第四种字体：Arial, 12号， 无背景色
        WritableFont subTextFont = new WritableFont(WritableFont.createFont("Arial"), 12);

        WritableCellFormat subTextFormat = new WritableCellFormat(subTextFont);
        

        // 单元格中的内容水平方居中
        subTextFormat.setAlignment(jxl.format.Alignment.CENTRE);

        subTextFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 该Excel中使用第五种字体：宋体, 12号， 无背景色
        WritableFont bold5 = new WritableFont(WritableFont.createFont("宋体"), 12);

        WritableCellFormat wcfFormat5 = new WritableCellFormat(bold5);

        // 单元格中的内容水平方向居左
        wcfFormat5.setAlignment(jxl.format.Alignment.CENTRE);

        wcfFormat5.setBorder(Border.ALL, BorderLineStyle.THIN);
        List<Integer> mergeRowList = new ArrayList<Integer>();
        
        if (null != orders)
        {
            int row = 4;
            for (int i = 0; i < orders.size(); ++i)
            {
                Order order = orders.get(i);
                List<OrderItem> items = order.getOrderItems();
                
                sheet.setRowView(row, 400);

                sheet.addCell(new Label(0, row, order.getCode(), subTextFormat));
                sheet.addCell(new Label(1, row, sdf.format(order.getTradeTime()), subTextFormat));
                sheet.addCell(new Label(2, row,order.getPlatform() == 1 ? "支付宝" : "饭粒支付", subTextFormat));
                
                for(int j = 0 ;j < items.size() ; j ++){
                    OrderItem item = items.get(j);
                    sheet.addCell(new Label(3, row + j, item.getGoodsCode(), subTextFormat));
                    sheet.addCell(new Label(4, row + j, item.getGoodsName(), subTextFormat));
                    sheet.addCell(new Label(5, row + j, item.getNum()+"", subTextFormat));
                    sheet.addCell(new jxl.write.Number(6, row + j, item.getCustomerIncome(), handNumberFormat(item.getCustomerIncome())));
                    sheet.addCell(new jxl.write.Number(7, row + j, item.getPrice(), handNumberFormat(item.getPrice())));
                    sheet.addCell(new jxl.write.Number(8, row + j, item.getTotalAmount(), handNumberFormat(item.getTotalAmount())));
                    sheet.addCell(new Label(9, row + j, 100-item.getPercent()+"", subTextFormat));
                }
                
                if(items.size() > 1){
                    mergeRowList.add(row);
                    // 合并单元格
                    sheet.mergeCells(0, row, 0, row+items.size()-1);
                    sheet.mergeCells(1, row, 1, row+items.size()-1);
                    sheet.mergeCells(2, row, 2, row+items.size()-1);
                }
                row += items.size();
            }
        }

        book.write();
        book.close();        
    }
    
    private static WritableCellFormat handNumberFormat(double number) throws WriteException{
        StringBuilder sb = new StringBuilder("0");
        BigDecimal bg = new BigDecimal(number+"");
        
        int scale = bg.scale();
        for(int i = 0 ; i < scale ; i ++){
            if(i == 0){
                sb.append(".");
            }
            sb.append("0");
        }
        
        LOGGER.info("number = " + number + ", format = " + sb.toString());
        
        jxl.write.NumberFormat nf = new jxl.write.NumberFormat(sb.toString());//设置数字格式
        WritableCellFormat format = new WritableCellFormat(nf); //设置表单格式
        // 该Excel中使用第四种字体：Arial, 12号， 无背景色
        WritableFont subTextFont = new WritableFont(WritableFont.createFont("Arial"), 12);
        format.setFont(subTextFont);
        format.setAlignment(jxl.format.Alignment.CENTRE);
        format.setBorder(Border.ALL, BorderLineStyle.THIN);
        return format;
    }
    
    private static WritableCellFormat handNumberFormat(BigDecimal number) throws WriteException{
        StringBuilder sb = new StringBuilder("0");
        if(number != null){
            int scale = number.scale();
            for(int i = 0 ; i < scale ; i ++){
                if(i == 0){
                    sb.append(".");
                }
                sb.append("0");
            }
        }
        LOGGER.info("number = " + number + ", format = " + sb.toString());
        jxl.write.NumberFormat nf = new jxl.write.NumberFormat(sb.toString());//设置数字格式
        WritableCellFormat format = new WritableCellFormat(nf); //设置表单格式
        // 该Excel中使用第二种字体：Arial，12号
        WritableFont subTitleFont = new WritableFont(WritableFont.createFont("Arial"), 12);
        format.setFont(subTitleFont);
        format.setAlignment(jxl.format.Alignment.CENTRE);
        format.setBorder(Border.ALL, BorderLineStyle.THIN);
        return format;
    }
    
    public static void exportReconciliationDetail(OutputStream outputStream,
            Reconciliation reconciliation, List<Order> orders) throws Exception{
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
        // 创建一个Workbook
        WritableWorkbook book = Workbook.createWorkbook(outputStream);

        // 该Excel中使用第一种字体：宋体，14号， 加粗
        WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), 14,
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);

        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        // 单元格中的内容水平方向居左
        titleFormat.setAlignment(jxl.format.Alignment.CENTRE);

        titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 标题的颜色，RGB值分别为：165、165、165
        Color color1 = Color.decode("#A5A5A5");

        book.setColourRGB(Colour.ORANGE, color1.getRed(), color1.getGreen(), color1.getBlue());

        Colour colour1 = Colour.ORANGE;

        titleFormat.setBackground(colour1);

        WritableSheet sheet = book.createSheet("对账单详情", 0);

        // 设置列宽
        sheet.setColumnView(0, 30);
        sheet.setColumnView(1, 25);
        sheet.setColumnView(2, 20);
        sheet.setColumnView(3, 20);
        sheet.setColumnView(4, 20);
        sheet.setColumnView(5, 25);
        sheet.setColumnView(6, 20);
        sheet.setColumnView(7, 22);
        sheet.setColumnView(8, 22);
        sheet.setColumnView(9, 17);
        sheet.setColumnView(10, 15);
        sheet.setColumnView(11, 20);

        // 第1行行高
        sheet.setRowView(0, 500);

        // 设置类型ws.addCell(new jxl.write.Label(列, 行, 内容.));
        sheet.addCell(new Label(0, 0, "对账单信息", titleFormat));
        sheet.addCell(new Label(1, 0, ""));
        sheet.addCell(new Label(2, 0, ""));
        sheet.addCell(new Label(3, 0, ""));
        sheet.addCell(new Label(4, 0, ""));
        sheet.addCell(new Label(5, 0, ""));
        sheet.addCell(new Label(6, 0, ""));
        sheet.addCell(new Label(7, 0, ""));
        sheet.addCell(new Label(8, 0, ""));
        sheet.addCell(new Label(9, 0, ""));
        sheet.addCell(new Label(10, 0, ""));
        sheet.addCell(new Label(11, 0, ""));
        
        // 合并单元格
        sheet.mergeCells(0, 0, 11, 0);

        // 第2行行高
        sheet.setRowView(1, 500);

        // 设置标题文字
        sheet.addCell(new Label(0, 1, "结算单号", titleFormat));
        sheet.addCell(new Label(1, 1, "", titleFormat));
        sheet.addCell(new Label(2, 1, "对账日期", titleFormat));
        sheet.addCell(new Label(3, 1, "", titleFormat));
        sheet.addCell(new Label(4, 1, "对账方名称", titleFormat));
        sheet.addCell(new Label(5, 1, "", titleFormat));
        sheet.addCell(new Label(6, 1, "累计单数", titleFormat));
        sheet.addCell(new Label(7, 1, "", titleFormat));
        sheet.addCell(new Label(8, 1, "交易总额（元）", titleFormat));
        sheet.addCell(new Label(9, 1, "", titleFormat));
        sheet.addCell(new Label(10, 1, "泛媒收入总额（元）", titleFormat));
        sheet.addCell(new Label(11, 1, "", titleFormat));

        // 合并单元格
        sheet.mergeCells(0, 1, 1, 1);
        sheet.mergeCells(2, 1, 3, 1);
        sheet.mergeCells(4, 1, 5, 1);
        sheet.mergeCells(6, 1, 7, 1);
        sheet.mergeCells(8, 1, 9, 1);
        sheet.mergeCells(10, 1, 11, 1);
        
        // 该Excel中使用第二种字体：Arial，12号
        WritableFont subTitleFont = new WritableFont(WritableFont.createFont("Arial"), 12);
        
        WritableCellFormat subTitleFormat = new WritableCellFormat(subTitleFont);

        // 单元格中的内容水平方向居左
        subTitleFormat.setAlignment(jxl.format.Alignment.CENTRE);

        subTitleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 数据的颜色，RGB值分别为：242、242、242
        Color color2 = Color.decode("#F2F2F2");

        book.setColourRGB(Colour.DARK_TEAL, color2.getRed(), color2.getGreen(), color2.getBlue());

        Colour colour2 = Colour.DARK_TEAL;

        subTitleFormat.setBackground(colour2);

        // 第3行行高
        sheet.setRowView(2, 400);

        sheet.addCell(new Label(0, 2, reconciliation.getReconciliationCode(), subTitleFormat));
        sheet.addCell(new Label(1, 2, "", subTitleFormat));
        sheet.addCell(new Label(2, 2, simpleDF.format(reconciliation.getReconciliationStartTime()) + "-" + simpleDF.format(reconciliation.getReconciliationEndTime()), subTitleFormat));
        sheet.addCell(new Label(3, 2, "", subTitleFormat));
        sheet.addCell(new Label(4, 2, reconciliation.getReconciliationShopName(), subTitleFormat));
        sheet.addCell(new Label(5, 2, "", subTitleFormat));
        sheet.addCell(new Label(6, 2, reconciliation.getOrderCount() + "", subTitleFormat));
        sheet.addCell(new Label(7, 2, "", subTitleFormat));
        sheet.addCell(new jxl.write.Number(8, 2, reconciliation.getTotalAmount().doubleValue(), handNumberFormat(reconciliation.getTotalAmount())));
        sheet.addCell(new Label(9, 2, "", subTitleFormat));
        sheet.addCell(new Label(10, 2, reconciliation.getPlatformAmount() + "", subTitleFormat));
        sheet.addCell(new Label(11, 2, "", subTitleFormat));
        // 合并单元格
        // 合并单元格
        sheet.mergeCells(0, 2, 1, 2);
        sheet.mergeCells(2, 2, 3, 2);
        sheet.mergeCells(4, 2, 5, 2);
        sheet.mergeCells(6, 2, 7, 2);
        sheet.mergeCells(8, 2, 9, 2);
        sheet.mergeCells(10, 2, 11, 2);
        
        // 第4行行高
        sheet.setRowView(3, 400);

        // 该Excel中使用第三种字体：宋体，14号， 加粗
        WritableFont textFont = new WritableFont(WritableFont.createFont("宋体"), 14,
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);

        WritableCellFormat textFormat = new WritableCellFormat(textFont);

        // 单元格中的内容水平方向居左
        textFormat.setAlignment(jxl.format.Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 表头的颜色，RGB值分别为：216、216、216
        Color color3 = Color.decode("#D8D8D8");

        book.setColourRGB(Colour.SEA_GREEN, color3.getRed(), color3.getGreen(), color3.getBlue());

        Colour colour3 = Colour.SEA_GREEN;

        textFormat.setBackground(colour3);
        sheet.setRowView(3, 500);

        sheet.addCell(new Label(0, 3, "订单编号", textFormat));
        sheet.addCell(new Label(1, 3, "交易时间", textFormat));
        sheet.addCell(new Label(2, 3, "支付来源", textFormat));
        sheet.addCell(new Label(3, 3, "商品编号", textFormat));
        sheet.addCell(new Label(4, 3, "交易商品", textFormat));
        sheet.addCell(new Label(5, 3, "交易数量（个）", textFormat));
        sheet.addCell(new Label(6, 3, "泛媒收入（元）", textFormat));
        sheet.addCell(new Label(7, 3, "对账方收入（元）", textFormat));
        sheet.addCell(new Label(8, 3, "交易手续费（元）", textFormat));
        sheet.addCell(new Label(9, 3, "分成比（%）", textFormat));
        sheet.addCell(new Label(10, 3, "单价（元）", textFormat));
        sheet.addCell(new Label(11, 3, "商品总价（元）", textFormat));
        
        // 该Excel中使用第四种字体：Arial, 12号， 无背景色
        WritableFont subTextFont = new WritableFont(WritableFont.createFont("Arial"), 12);

        WritableCellFormat subTextFormat = new WritableCellFormat(subTextFont);
        

        // 单元格中的内容水平方居中
        subTextFormat.setAlignment(jxl.format.Alignment.CENTRE);

        subTextFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        // 该Excel中使用第五种字体：宋体, 12号， 无背景色
        WritableFont bold5 = new WritableFont(WritableFont.createFont("宋体"), 12);

        WritableCellFormat wcfFormat5 = new WritableCellFormat(bold5);

        // 单元格中的内容水平方向居左
        wcfFormat5.setAlignment(jxl.format.Alignment.CENTRE);

        wcfFormat5.setBorder(Border.ALL, BorderLineStyle.THIN);
        List<Integer> mergeRowList = new ArrayList<Integer>();
        
        if (null != orders)
        {
            int row = 4;
            for (int i = 0; i < orders.size(); ++i)
            {
                Order order = orders.get(i);
                List<OrderItem> items = order.getOrderItems();
                
                sheet.setRowView(row, 400);

                sheet.addCell(new Label(0, row, order.getCode(), subTextFormat));
                sheet.addCell(new Label(1, row, sdf.format(order.getTradeTime()), subTextFormat));
                sheet.addCell(new Label(2, row,order.getPlatform() == 1 ? "支付宝" : "饭粒支付", subTextFormat));
                
                for(int j = 0 ;j < items.size() ; j ++){
                    OrderItem item = items.get(j);
                    sheet.addCell(new Label(3, row + j, item.getGoodsCode(), subTextFormat));
                    sheet.addCell(new Label(4, row + j, item.getGoodsName(), subTextFormat));
                    sheet.addCell(new Label(5, row + j, item.getNum()+"", subTextFormat));
                    sheet.addCell(new jxl.write.Number(6, row + j, item.getPlatIncome(), handNumberFormat(item.getPlatIncome())));
                    sheet.addCell(new jxl.write.Number(7, row + j, item.getCustomerIncome(), handNumberFormat(item.getCustomerIncome())));
                    sheet.addCell(new jxl.write.Number(8, row + j, item.getTradeFee(), handNumberFormat(item.getTradeFee())));
                    sheet.addCell(new Label(9, row + j, item.getPercent()+"", subTextFormat));
                    sheet.addCell(new jxl.write.Number(10, row + j, item.getPrice(), handNumberFormat(item.getPrice())));
                    sheet.addCell(new jxl.write.Number(11, row + j, item.getTotalAmount(), handNumberFormat(item.getTotalAmount())));
                }
                
                if(items.size() > 1){
                    mergeRowList.add(row);
                    // 合并单元格
                    sheet.mergeCells(0, row, 0, row+items.size()-1);
                    sheet.mergeCells(1, row, 1, row+items.size()-1);
                    sheet.mergeCells(2, row, 2, row+items.size()-1);
                }
                row += items.size();
            }
        }

        book.write();
        book.close();        
    }
    
}
