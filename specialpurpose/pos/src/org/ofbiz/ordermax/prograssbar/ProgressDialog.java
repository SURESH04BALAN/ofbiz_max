/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.prograssbar;

/**
 *
 * @author siranjeev
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ProgressDialog extends JDialog
        implements ProgressReporter, ActionListener {

    public static final int K = 1024;

    protected SimpleDateFormat timeFormatter;
    protected NumberFormat numFormatter;

    protected JProgressBar progress;
    protected JButton button;
    protected JLabel source, target,
            status, timing, percent;
    protected boolean cancelled = false;
    protected long mark = System.currentTimeMillis();
    protected int current, size;

    public ProgressDialog(JFrame parent, String title,
            String sourcePath, String targetPath, int size) {
        super(parent, title);

        timeFormatter = new SimpleDateFormat("HH:mm:ss");
        timeFormatter.setTimeZone(
                TimeZone.getTimeZone("GMT"));
        numFormatter
                = NumberFormat.getInstance().getPercentInstance();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        setSize(375, 191);

        JPanel promptPanel = new JPanel();
        promptPanel.setPreferredSize(new Dimension(70, 80));
        promptPanel.setLayout(new GridLayout(4, 1));
        promptPanel.add(new JLabel("Source:"));
        promptPanel.add(new JLabel("Target:"));
        promptPanel.add(new JLabel("Status:"));
        promptPanel.add(new JLabel("Time     Left:"));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1));
        infoPanel.add(source = new JLabel());
        infoPanel.add(target = new JLabel());
        infoPanel.add(status = new JLabel());
        infoPanel.add(timing = new JLabel());

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setBorder(
                new EmptyBorder(5, 10, 5, 10));
        messagePanel.add("West", promptPanel);
        messagePanel.add("Center", infoPanel);

        panel.add("North", messagePanel);

        progress = new JProgressBar();
        progress.setValue(50);
        progress.setPreferredSize(new Dimension(300, 20));
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new BorderLayout());
        progressPanel.setBorder(
                new EmptyBorder(3, 10, 3, 10));
        progressPanel.add("Center", progress);
        percent = new JLabel("");
        percent.setPreferredSize(new Dimension(45, 23));
        progressPanel.add("East", percent);
        panel.add("Center", progressPanel);

        button = new JButton("Cancel");
        button.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(5, 0, 7, 0));
        buttonPanel.add(button);
        panel.add("South", buttonPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

//        setVisible(true);
        setSource(sourcePath);
        setTarget(targetPath);
        setSize(size);
    }

    public void setSource(String name) {
        source.setText(name);//collapsePath(source, name));
    }

    public void setTarget(String name) {
        target.setText(name);//collapsePath(target, name));
    }

    public void setSize(int size) {
        this.size = size;
        progress.setMaximum(size);
        mark = System.currentTimeMillis();
        current = 0;
    }

    @Override
    public void setProgress(String str) {
        target.setText(str);
        this.current++;
        setProgress(current);
    }

    @Override
    public void setProgress(int current) {
        this.current = current;
        float per = (float) current / (float) size;

        percent.setText(" " + numFormatter.format(per));
        progress.setValue(current);
//        updateStatus();
        timing.setText( formatTime(timeLeftInMillis(current)));
 

        if (cancelled || current >= size) {
            // If completed, give     the dialog a chance
            // to hit 100% before     disposing of it.
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException e) {
            }
            setVisible(false);
            dispose();
        }
    }

    private String collapsePath(JLabel field, String text) {
        FontMetrics metrics
                = getGraphics().getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        int fieldWidth = field.getSize().width;
        if (textWidth > fieldWidth) {
            int center = text.length() / 3;
            String left = text.substring(0, center);
            String right = text.substring(center + 1);
            while (metrics.stringWidth(text) > fieldWidth) {
                left = left.substring(0, left.length() - 1);
                right = right.substring(1);
                text = left + "..." + right;
            }
        }
        return text;
    }

    private void updateStatus() {
        String stat = "" + formatSize(current)
                + " of " + formatSize(size) + " (at "
                + formatSize(bytesPerSecond(current)) + "/sec)";
        status.setText(stat);
    }

    private String formatSize(int size) {
        if (size >= K) {
            return "" + (size / K) + "K";
        } else {
            return "" + size;
        }
    }

    private int bytesPerSecond(int current) {
        long elapsed = System.currentTimeMillis() - mark;
        double speed = current / elapsed;
        return (int) (1000 * speed);
    }

    public int timeLeftInMillis(int current) {
        long elapsed = System.currentTimeMillis() - mark;
        double speed = elapsed / (double) current;
        double remaining = size - current;
        int expected = (int) (speed * remaining);
        return expected + 1;
    }

    private String formatTime(long millis) {
        return timeFormatter.format(new Date(millis));
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() instanceof JButton) {
            cancelled = true;
        }
    }

}
