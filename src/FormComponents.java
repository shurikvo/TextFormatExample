import java.text.NumberFormat;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FormComponents extends JFrame implements ItemListener {
    private JFrame frame;

    private JLabel lblInfo = null, lblFormatted;
    private JTextArea strResult = null;
    private JPanel pnlData, pnlBottom, pnlButtons;
    private JButton btnCancel, btnCommit;
    private JFormattedTextField strMoney;

    private void addComponentsToPane(Container pane) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        Border blackline = BorderFactory.createLineBorder(Color.black);
        // Log area: -------------------------------------------------------------------
        {
            lblInfo = new JLabel();
            strResult = new JTextArea("-------------------------------------------------");
            strResult.setEditable(false);
            strResult.setBackground(Color.black);
            strResult.setForeground(Color.white);
            strResult.setFont(new Font("Courier New", Font.PLAIN, 12));
            JScrollPane scroll = new JScrollPane(strResult,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            pane.add(scroll, BorderLayout.CENTER);
        }
        // Bottom area: ----------------------------------------------------------------
        {
            pnlBottom = new JPanel();
            pnlBottom.setLayout(new BorderLayout());
            pane.add(pnlBottom, BorderLayout.PAGE_END);

            lblInfo = new JLabel("");
            lblInfo.setBorder(blackline);
            pnlBottom.add(lblInfo, BorderLayout.CENTER);

            pnlButtons = new JPanel();
            pnlButtons.setLayout(null);
            pnlButtons.setPreferredSize(new Dimension(210, 20));
            pnlBottom.add(pnlButtons, BorderLayout.LINE_END);

            int offBot = 2, wBotBut = 100, hBotBut = 20;

            btnCancel = new JButton("Cancel");
            btnCancel.setBounds(offBot, 0, wBotBut, hBotBut);
            btnCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    doCancel();
                }
            });
            pnlButtons.add(btnCancel);

            btnCommit = new JButton("Commit");
            btnCommit.setBounds(offBot + wBotBut + 3, 0, wBotBut, hBotBut);
            btnCommit.addItemListener(this);
            btnCommit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    doCommit();
                }
            });
            pnlButtons.add(btnCommit);
        }
        // Data area: ------------------------------------------------------------------
        {
            pnlData = new JPanel();
            pnlData.setLayout(null);
            pnlData.setPreferredSize(new Dimension(300, 300));
            pane.add(pnlData, BorderLayout.PAGE_START);

            lblFormatted = new JLabel("Money:", SwingConstants.RIGHT);
            lblFormatted.setBounds(10,10,70,20);
            pnlData.add(lblFormatted);


            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            strMoney = new JFormattedTextField(numberFormat);
            strMoney.setBounds(85,10,150,20);
            strMoney.setValue(new Double(100.1));
            pnlData.add(strMoney);
        }
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
    }

    private void doCancel(){
        WriteMsg("Done: Cancel");
    }
}
