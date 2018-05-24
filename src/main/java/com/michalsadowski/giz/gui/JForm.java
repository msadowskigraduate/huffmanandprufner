package com.michalsadowski.giz.gui;

import com.michalsadowski.giz.huffman.HuffmanEncoder;
import com.michalsadowski.giz.huffman.HuffmanRunner;
import com.michalsadowski.giz.huffman.HuffmanTreeBuilder;
import com.michalsadowski.giz.huffman.service.HuffmanUtils;
import com.michalsadowski.giz.prufer.PruferDecoder;
import com.michalsadowski.giz.prufer.PruferEncoder;
import com.michalsadowski.giz.prufer.PruferRunner;
import com.michalsadowski.giz.prufer.domain.PruferCode;
import com.michalsadowski.giz.services.HuffmanFileReader;
import com.michalsadowski.giz.services.PruferFileReader;
import com.michalsadowski.giz.services.exception.InvalidFileException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.michalsadowski.giz.gui.GUIConstants.*;

/**
 * Created by sadowsm3 on 22.05.2018
 */
public class JForm {

    private HuffmanRunner huffmanRunner;
    private PruferRunner pruferRunner;
    private PruferDecoder pruferDecoder;

    private JPanel mainPanel;
    private JTree FileExplorer;
    private JButton fileChooseButton;
    private JLabel StatusLabel;
    private JTextArea textArea1;
    private JButton button1;
    private JButton button2;

    public JForm() {
        huffmanRunner = new HuffmanRunner(new HuffmanEncoder(), new HuffmanTreeBuilder());
        pruferRunner = new PruferRunner(new PruferEncoder());
        pruferDecoder = new PruferDecoder();

        $$$setupUI$$$();
        fileChooseButton.addActionListener(e -> {
            String filePath = FileExplorer.getSelectionPath().toString().replace(", ", "\\").replace("[", "").replace("]", "").trim();
            System.out.println(filePath);
            if (Arrays.stream(SUPPORTED_FILE_TYPES.split(",")).anyMatch(filePath::endsWith)) {
                StatusLabel.setText(filePath);
                setButtonState(true);
            } else {
                StatusLabel.setText(UNSUPPORTED_FILE);
            }
        });
        button1.addActionListener(e -> {
            try {
                clearTextArea();
                String fileContents = HuffmanFileReader.readFromFile(new File(StatusLabel.getText()).toPath());
                StatusLabel.setText(ANALYZING);
                huffmanRunner.run(fileContents);
                List<Integer> pruferCode = pruferRunner.encode(huffmanRunner.getValuemap());
                StatusLabel.setText(GUIConstants.DONE);
                textArea1.append(PRUFER_CODE_PREFIX + pruferCode.toString() + "\n");
                textArea1.append(HUFFMAN_ENCODING_MAP_PREFIX + HuffmanUtils.stringfyEncodingMap(huffmanRunner.getEncodingMap()));
            } catch (InvalidFileException el) {
                StatusLabel.setText(UNSUPPORTED_FILE_CONTENTS);
            }
            setButtonState(false);
        });

        button2.addActionListener(e -> {
            PruferCode fileContents;
            try {
                clearTextArea();
                fileContents = PruferFileReader.readFromFile(new File(StatusLabel.getText()).toPath());
                pruferDecoder.decode(fileContents);
                textArea1.append(COMPUTATION_DONE);
                textArea1.append("\n");
            } catch (InvalidFileException e1) {
                StatusLabel.setText(UNSUPPORTED_FILE_CONTENTS);
            }
            setButtonState(false);
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        FileExplorer = new JTree();
        FileExplorer.setModel(new FileSystemModel(new File("C:\\Users")));
    }

    private void setButtonState(boolean state) {
        button1.setEnabled(state);
        button2.setEnabled(state);
    }

    private void clearTextArea() {
        textArea1.setText("");
    }
    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMinimumSize(new Dimension(720, 500));
        mainPanel.setPreferredSize(new Dimension(720, 500));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        FileExplorer.setEditable(false);
        FileExplorer.setRootVisible(false);
        FileExplorer.setShowsRootHandles(true);
        scrollPane1.setViewportView(FileExplorer);
        StatusLabel = new JLabel();
        StatusLabel.setText("Choose file to continue.");
        panel1.add(StatusLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(1, 1), null, null, 1, false));
        fileChooseButton = new JButton();
        fileChooseButton.setText("Choose File");
        panel1.add(fileChooseButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(191, 30), null, 0, false));
        button1 = new JButton();
        button1.setEnabled(false);
        button1.setText("Encode Huffman");
        panel1.add(button1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button2 = new JButton();
        button2.setEnabled(false);
        button2.setText("Decode Prufer Code");
        panel1.add(button2, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textArea1 = new JTextArea();
        panel1.add(textArea1, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(480, 360), new Dimension(480, 360), new Dimension(480, 360), 0, false));
        final JToolBar toolBar1 = new JToolBar();
        mainPanel.add(toolBar1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(1, 1), null, null, 1, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(10, 10), new Dimension(10, 10), new Dimension(10, 10), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
