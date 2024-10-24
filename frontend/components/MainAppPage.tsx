// MainAppPage.tsx
import React, { useState } from 'react';
import { View, StyleSheet, Text } from 'react-native'; // Added Text import
import TopBarGeoTagR from './TopBarGeoTagR';
import SidePanel from './SidePanel';
import ImageEditor from './ImageEditor';

interface ImageData {
  uri: string;
  name: string;
}

const MainAppPage = () => {
  const [images, setImages] = useState<ImageData[]>([]); // State to hold added images
  const [selectedImage, setSelectedImage] = useState<ImageData | null>(null); // State for selected image

  const handleAddImage = (image: ImageData) => {
    setImages((prevImages) => [...prevImages, image]); // Add new image to the list
  };

  const handleSelectImage = (image: ImageData) => {
    setSelectedImage(image); // Set selected image for editing
  };

  return (
    <View style={styles.container}>
      <TopBarGeoTagR />
      <View style={styles.body}>
        <SidePanel images={images} onAddImage={handleAddImage} onSelectImage={handleSelectImage} />
        <View style={styles.mainContent}>
          {selectedImage ? (
            <ImageEditor image={selectedImage} />
          ) : (
            <Text style={styles.placeholderText}>Select an image to edit</Text>
          )}
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#000000',
    flex: 1,
  },
  body: {
    flexDirection: 'row',
    flex: 1,
  },
  mainContent: {
    flex: 1,
    padding: 20,
  },
  placeholderText: {
    textAlign: 'center',
    fontSize: 18,
    color: '#888',
  },
});

export default MainAppPage;
