package com.michalsadowski.giz.gui;

import com.michalsadowski.giz.huffman.HuffmanEncoder;
import com.michalsadowski.giz.huffman.HuffmanRunner;
import com.michalsadowski.giz.huffman.HuffmanTreeBuilder;
import com.michalsadowski.giz.prufer.PruferDecoder;
import com.michalsadowski.giz.prufer.PruferEncoder;
import com.michalsadowski.giz.prufer.PruferRunner;
import com.michalsadowski.giz.prufer.domain.PruferCode;
import com.michalsadowski.giz.services.HuffmanFileReader;
import com.michalsadowski.giz.services.PruferFileReader;
import com.michalsadowski.giz.services.exception.InvalidFileException;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

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

        fileChooseButton.addActionListener(e -> {
            String filePath = FileExplorer.getSelectionPath().toString().replace(", ", "\\").replace("[", "").replace("]", "").trim();
            System.out.println(filePath);
            if (Arrays.stream(GUIConstants.SUPPORTED_FILE_TYPES.split(",")).anyMatch(filePath::endsWith)) {
                StatusLabel.setText(filePath);
                button1.setEnabled(true);
                button2.setEnabled(true);
            } else {
                StatusLabel.setText(GUIConstants.UNSUPPORTED_FILE);
            }
        });
        button1.addActionListener(e -> {
            try {
                String fileContents = HuffmanFileReader.readFromFile(new File(StatusLabel.getText()).toPath());
                StatusLabel.setText(GUIConstants.ANALYZING);
                huffmanRunner.run(fileContents);
                List<Integer> pruferCode = pruferRunner.encode(huffmanRunner.getValuemap());
                StatusLabel.setText(GUIConstants.DONE);
                textArea1.append(GUIConstants.PRUFER_CODE + pruferCode.toString());
            } catch(InvalidFileException el) {
                StatusLabel.setText(GUIConstants.UNSUPPORTED_FILE_CONTENTS);
            }
        });

        button2.addActionListener(e -> {
            PruferCode fileContents;
            try {
                fileContents = PruferFileReader.readFromFile(new File(StatusLabel.getText()).toPath());
                String message = pruferDecoder.decode(fileContents);
                textArea1.append(message);
            } catch (InvalidFileException e1) {
                StatusLabel.setText(GUIConstants.UNSUPPORTED_FILE_CONTENTS);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        FileExplorer = new JTree();
        FileExplorer.setModel(new FileSystemModel(new File("C:\\Users")));
    }
}
