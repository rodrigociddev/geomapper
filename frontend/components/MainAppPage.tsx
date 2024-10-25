import React, { useState } from 'react';
import { View, StyleSheet, Text } from 'react-native';
import TopBarGeoTagR from './TopBarGeoTagR';
import SidePanel from './SidePanel';
import ImageEditor from './ImageEditor';

interface ImageData {
  uri: string;
  name: string;
}

const MainAppPage = () => {
  const [images, setImages] = useState<ImageData[]>([]);
  const [selectedImage, setSelectedImage] = useState<ImageData | null>(null);
  const [selectedImages, setSelectedImages] = useState<{ [key: string]: boolean }>({});

  const handleAddImage = (image: ImageData) => {
    console.log('Adding image:', image);
    setImages((prevImages) => [...prevImages, image]);
  };

  const handleSelectImage = (image: ImageData) => {
    console.log('Selected image for editing:', image);
    setSelectedImage(image);
  };

  const handleSelectAll = () => {
    const allSelected = images.reduce((acc, image, index) => {
      acc[index] = true;
      return acc;
    }, {} as { [key: string]: boolean });
    console.log('Selecting all images:', allSelected);
    setSelectedImages(allSelected);
  };

  const handleDeselectAll = () => {
    const noneSelected = images.reduce((acc, image, index) => {
      acc[index] = false;
      return acc;
    }, {} as { [key: string]: boolean });
    console.log('Deselecting all images:', noneSelected);
    setSelectedImages(noneSelected);
  };

  const handleDeleteSelected = () => {
    console.log('Selected images for deletion:', selectedImages);

    const updatedImages = images.filter((image, index) => !selectedImages[index]);
    
    // If the current selected image is being deleted, clear it
    if (selectedImage && selectedImages[images.indexOf(selectedImage)]) {
      console.log('The selected image is being deleted, resetting selectedImage.');
      setSelectedImage(null);
    }

    console.log('Updated image list after deletion:', updatedImages);
    setImages(updatedImages);
    setSelectedImages({});
  };

  return (
    <View style={styles.container}>
      <TopBarGeoTagR 
        onSelectAll={handleSelectAll} 
        onDeselectAll={handleDeselectAll} 
        onDeleteSelected={handleDeleteSelected}
      />
      <View style={styles.body}>
        <SidePanel
          images={images}
          selectedImages={selectedImages}
          onAddImage={handleAddImage}
          onSelectImage={handleSelectImage}
          setSelectedImages={setSelectedImages}
        />
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
