import React, { useState } from 'react';
import { View, Text, StyleSheet, Image, TextInput, ScrollView } from 'react-native';

interface ImageEditorProps {
  image: {
    uri: string;
    name: string;
  };
}

const ImageEditor: React.FC<ImageEditorProps> = ({ image }) => {
  const [title, setTitle] = useState('');
  const [annotations, setAnnotations] = useState('');

  return (
    <View style={styles.container}>
      {/* Title Input */}
      <TextInput
        style={styles.titleInput}
        placeholder="Enter Title..."
        value={title}
        onChangeText={setTitle}
      />

      {/* Display Selected Image */}
      <View style={styles.imageContainer}>
        <Image 
          source={{ uri: image.uri }} 
          style={styles.image} 
          resizeMode="cover" // Use cover to fill the space, or change to contain
          resizeMethod="scale" // Helps improve the scaling process
        />
      </View>

      {/* Annotations Input */}
      <ScrollView style={styles.annotationContainer}>
        <TextInput
          style={styles.annotationInput}
          placeholder="Add your annotations here..."
          value={annotations}
          onChangeText={setAnnotations}
          multiline={true}
        />
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    alignItems: 'center',
  },
  titleInput: {
    width: '80%',
    padding: 10,
    fontSize: 18,
    borderBottomWidth: 1,
    borderColor: '#ccc',
    marginBottom: 20,
  },
  imageContainer: {
    width: 400, // Adjust width for a larger square
    height: 400, // Ensure height matches width for a square aspect ratio
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 20,
    backgroundColor: '#f0f0f0', // Light background to highlight the image area
    overflow: 'hidden', // Ensure any overflow is hidden to maintain a square shape
    borderRadius: 10, // Optional: add rounded corners to the image container
  },
  image: {
    width: '100%', // Ensures the image fills the container's width
    height: '100%', // Ensures the image fills the container's height
    resizeMode: 'cover', // Fill the area, cropping if necessary
    borderRadius: 10, // Match border radius for smoothness
  },
  annotationContainer: {
    width: '80%',
    padding: 10,
    flex: 1,
    backgroundColor: '#f8f8f8',
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#ccc',
  },
  annotationInput: {
    fontSize: 16,
    textAlignVertical: 'top', // Ensures text starts from the top in multiline mode
  },
});

export default ImageEditor;
