package org.example;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.lang.GeoLocation;
import com.drew.metadata.exif.GpsDirectory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

public class DemoFile {
    private JButton chooseFileButton;
    private JButton searchButton;
    private JPanel panel1;
    private JTextField searchTextField;
    private JTextArea resultArea;
    private JLabel label1;

    private Project project;

    private HashMap<String, Media> mediaLookUp;

    public DemoFile() {

        project = new Project();

        // sep components
        chooseFileButton = new JButton("Open Image File");
        searchButton = new JButton("Search");
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        label1 = new JLabel("GeoTag METADATA DEMO");
        searchTextField = new JTextField();

        // panes
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(label1, BorderLayout.NORTH);
        panel1.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel2.add(searchTextField);
        panel2.add(searchButton);

        //sets panel(2) w search functions at the botton of the result text box
        panel1.add(panel2, BorderLayout.SOUTH);
        panel1.add(chooseFileButton, BorderLayout.WEST);

        //component manipulation
        label1.setForeground(Color.blue);
        label1.setHorizontalAlignment(JLabel.CENTER);

        searchTextField.setPreferredSize(new Dimension(400,50));

        // creation of frame
        JFrame frame = new JFrame("Image Metadata Reader");
        frame.setSize(600, 600);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // button functionality
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true); 
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooser.getSelectedFiles();
                    processFiles(files);
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textFieldValue = searchTextField.getText();
                Media foundMedia =project.getMediaByName(textFieldValue);

                if(foundMedia == null){
                    resultArea.append("No such file with name " + textFieldValue + " found \n");
                } else {
                    resultArea.append("Media Found \n" +
                            "Name: " + foundMedia.getName() + "\n" +
                            "Latitude: " + foundMedia.getLatitude() + "\n" +
                            "Longitude: " + foundMedia.getLongitude() + "\n\n");
                }
            }
        });
    }

    private void processFiles(File[] files) {
        mediaLookUp = new HashMap<>();

        for (File file : files) {
            if (!file.exists()) {
                resultArea.setText("File does not exist: " + file.getAbsolutePath());
                return;
            }


            try {
                // metadata reading
                Metadata metadata = ImageMetadataReader.readMetadata(file);
                GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

                Media media = new Media();
                media.setName(file.getName());

                if (gpsDirectory != null) {
                    GeoLocation geoLocation = gpsDirectory.getGeoLocation();

                    if (geoLocation != null) {
                        media.setLatitude(geoLocation.getLatitude());
                        media.setLongitude(geoLocation.getLongitude());

                        if ((media.getLatitude() == 0.0) && (media.getLongitude() == 0.0)) {
                            media.setName(file.getName());
                            resultArea.append("No GPS");
                        }

                    } else  {
                        media.setName(file.getName());
                        resultArea.append("No GPS");
                    }

                } else {
                    media.setName(file.getName());
                    resultArea.append("No GPS");
                }

                //adds to the hashmap, key is the file name
                project.addMedia(media);


                resultArea.append("Name: " + media.getName() + "\n" +
                        "Latitude: " + media.getLatitude() + "\n" +
                        "Longitude: " + media.getLongitude() + "\n\n");

            } catch (Exception ex) {
                resultArea.setText("Error reading the image metadata: " + ex.getMessage());
            }
        }
    }
}
