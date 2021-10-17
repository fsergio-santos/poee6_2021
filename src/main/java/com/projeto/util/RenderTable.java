package com.projeto.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderTable extends DefaultTableCellRenderer {

	
	private static final long serialVersionUID = -8748067302424181304L;
	
	private DecimalFormat numberFormat = new DecimalFormat("###.##)");

	private static final Color EVEN_COLOR = new Color(240, 240, 255);
      
    public RenderTable() {
    	
    }
    
   	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JLabel label = new JLabel(value.toString());
        label.setFont(new Font("Verdana", Font.BOLD, 18));
        label.setForeground(table.getTableHeader().getForeground());
        label.setHorizontalTextPosition(JLabel.CENTER);
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(row % 2 == 0 ? EVEN_COLOR : table.getBackground());
        }
        if ( value instanceof Number) {
            Number num = (Number) value;
            String text = numberFormat.format(num);
            label.setText(text);
            label.setForeground(num.doubleValue() < 0 ? Color.RED : Color.BLACK);
            setHorizontalAlignment(RIGHT);
        }
        if(value instanceof Date){
            String strDate = new SimpleDateFormat("dd/MM/yyyy").format((Date)value);
            label.setText(strDate);
            label.setHorizontalAlignment(JLabel.CENTER);
            setHorizontalAlignment(CENTER);
        }
         if (value instanceof String ) {
        	setHorizontalAlignment(LEFT);
        }
        return this;
	}

}
