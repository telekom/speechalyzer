package com.felix.util.image;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;

public class ClipImage {

  @SuppressWarnings("deprecation")
public static void main(String args[]) {

    JFrame frame = new JFrame("Clip Image");
    Container contentPane = frame.getContentPane();

    final Clipboard clipboard = frame.getToolkit().getSystemClipboard();

    Icon icon = new ImageIcon("jaeger.jpg");
    final JLabel label = new JLabel(icon);
    label.setTransferHandler(new ImageSelection());

    JScrollPane pane = new JScrollPane(label);
    contentPane.add(pane, BorderLayout.CENTER);

    JButton copy = new JButton("Copy");
    copy.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TransferHandler handler = label.getTransferHandler();
        handler.exportToClipboard(label, clipboard,
            TransferHandler.COPY);
      }
    });

    JButton clear = new JButton("Clear");
    clear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        label.setIcon(null);
      }
    });

    JButton paste = new JButton("Paste");
    paste.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        Transferable clipData = clipboard.getContents(clipboard);
        if (clipData != null) {
          if (clipData.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            TransferHandler handler = label.getTransferHandler();
            handler.importData(label, clipData);
          }
        }
      }
    });

    JPanel p = new JPanel();
    p.add(copy);
    p.add(clear);
    p.add(paste);
    contentPane.add(p, BorderLayout.SOUTH);
    frame.setSize(300, 300);
    frame.show();
  }
}