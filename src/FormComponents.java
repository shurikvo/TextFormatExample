import java.text.*;
import java.util.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import java.awt.*;

public class FormComponents extends JFrame implements ItemListener {
    private JFrame frame;

    private JLabel lblInfo = null;
    private JTextArea strResult = null;
    private JFormattedTextField strMoney, strDate, strPercent, strFormat;

    private static final DateFormat dteFormat = new SimpleDateFormat("dd.MM.yyyy");

    private void addComponentsToPane(Container pane) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        Border blackline = BorderFactory.createLineBorder(Color.black);
        // Log area: -------------------------------------------------------------------
        lblInfo = new JLabel();
        strResult = new JTextArea("-------------------------------------------------");
        strResult.setEditable(false);
        strResult.setBackground(Color.black);
        strResult.setForeground(Color.white);
        strResult.setFont(new Font("Courier New", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(strResult,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.add(scroll, BorderLayout.CENTER);
        // Bottom area: ----------------------------------------------------------------
        JPanel pnlBottom = new JPanel();
        pnlBottom.setLayout(new BorderLayout());
        pane.add(pnlBottom, BorderLayout.PAGE_END);

        lblInfo = new JLabel("");
        lblInfo.setBorder(blackline);
        pnlBottom.add(lblInfo, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(null);
        pnlButtons.setPreferredSize(new Dimension(210, 20));
        pnlBottom.add(pnlButtons, BorderLayout.LINE_END);

        int offBot = 2, wBotBut = 100, hBotBut = 20;

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(offBot, 0, wBotBut, hBotBut);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doCancel();
            }
        });
        pnlButtons.add(btnCancel);

        JButton btnCommit = new JButton("Commit");
        btnCommit.setBounds(offBot + wBotBut + 3, 0, wBotBut, hBotBut);
        btnCommit.addItemListener(this);
        btnCommit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doCommit();
            }
        });
        pnlButtons.add(btnCommit);
        // Data area: ------------------------------------------------------------------
        JPanel pnlData = new JPanel();
        pnlData.setLayout(null);
        pnlData.setPreferredSize(new Dimension(300, 300));
        pane.add(pnlData, BorderLayout.PAGE_START);

        JLabel lblMoney = new JLabel("Money:", SwingConstants.RIGHT);
        lblMoney.setBounds(10,10,70,20);
        pnlData.add(lblMoney);

        strMoney = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        strMoney.setBounds(85,10,150,20);
        strMoney.setValue(100.1);
        pnlData.add(strMoney);

        JLabel lblDate = new JLabel("Date:", SwingConstants.RIGHT);
        lblDate.setBounds(10,30,70,20);
        pnlData.add(lblDate);

        strDate = new JFormattedTextField(dteFormat);
        strDate.setBounds(85,30,150,20);
        strDate.setColumns(20);
        pnlData.add(strDate);
        try {
            MaskFormatter dateMask = new MaskFormatter("##.##.####");
            dateMask.install(strDate);
        } catch (ParseException ex) {
            WriteMsg("addComponentsToPane: "+ ex);
        }

        JLabel lblPercent = new JLabel("Percent:", SwingConstants.RIGHT);
        lblPercent.setBounds(10,50,70,20);
        pnlData.add(lblPercent);

        strPercent = new JFormattedTextField(NumberFormat.getPercentInstance());
        strPercent.setBounds(85,50,150,20);
        strPercent.setValue(.35);
        pnlData.add(strPercent);

        JLabel lblFormat = new JLabel("Format:", SwingConstants.RIGHT);
        lblPercent.setBounds(10,70,70,20);
        pnlData.add(lblPercent);

        try {
            strFormat = new JFormattedTextField(new MaskFormatter("#(###)###-##-##"));
        } catch (ParseException e) {
            WriteMsg("addComponentsToPane: "+ e);
        }
        try {
            MaskFormatter formatMask = new MaskFormatter("#(###)###-##-##");
            formatMask.install(strFormat);
        } catch (ParseException ex) {
            WriteMsg("addComponentsToPane: "+ ex);
        }
        strFormat.setBounds(85,70,150,20);
        pnlData.add(strFormat);
        //------------------------------------------------------------------------------
    }

    public void createAndShowGUI(){
        frame = new JFrame("FormMain");
        frame.setTitle("Text Format example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame.getContentPane());
        frame.pack();
        centerFrame();
        frame.setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    private void centerFrame() {
        Dimension windowSize = frame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;
        frame.setLocation(dx, dy);
    }

    private void WriteMsg(String sMes){
        if (lblInfo != null) lblInfo.setText(sMes);
        if (strResult != null) strResult.setText(strResult.getText() + "\n" + sMes);
    }

    private void doCommit(){
        WriteMsg("Done: Commit");
        WriteMsg("Money: " + strMoney.getValue());
        WriteMsg("Date: " + strDate.getText() + " -> " + strDate.getValue());
        WriteMsg("Percent: " + strPercent.getValue());
        WriteMsg("Format: " + strFormat.getValue());
    }

    private void doCancel(){
        WriteMsg("Done: Cancel");
    }
}
