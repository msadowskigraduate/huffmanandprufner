package com.michalsadowski.giz;

import com.michalsadowski.giz.huffman.HuffmanEncoder;
import com.michalsadowski.giz.huffman.HuffmanRunner;
import com.michalsadowski.giz.huffman.HuffmanTreeBuilder;
import com.michalsadowski.giz.prufer.PruferDecoder;
import com.michalsadowski.giz.prufer.PruferEncoder;
import com.michalsadowski.giz.prufer.PruferRunner;
import com.michalsadowski.giz.prufer.domain.PruferCode;

import java.util.Arrays;

/**
 * Created by sadowsm3 on 19.05.2018
 */
public class Main {

    public static void main(String[] args) {

//        HuffmanRunner runner = new HuffmanRunner(new HuffmanEncoder(), new HuffmanTreeBuilder());
////        runner.run("The syntax of a style sheet is very similar to the one seen in the cascading style sheets of HTML. The CSS of GraphStream mostly follows the same rules, including inheritance and composition of styles. A style sheet is a sequence of styling rules. A styling rule is made of a selector and a set of style properties.");
//        runner.run("The properties.");
//        PruferRunner prunner = new PruferRunner(new PruferEncoder());
//        prunner.encode(runner.valuemap);

        PruferCode code = new PruferCode();
        code.setRootNode("1");
        code.setIdentifierList(new String[] {"1", "3", "6", "11", "5", "5", "3", "6", "11"});
        code.setNodeList(new String[] {"a", ";", "e", "c", "b", "d"});
        PruferDecoder decoder = new PruferDecoder();
        decoder.decode(code);
    }
}