import React, { useState } from 'react';
import { View, StyleSheet, Text } from 'react-native';
import TopBarGeoTagR from './TopBarGeoTagR';
import SidePanel from './SidePanel';
import ImageEditor from './ImageEditor';

interface ImageData {
  uri: string;
  name: string;
  title?: string;
  latitude?: string;
  longitude?: string;
  annotation?: string;
}

const MainAppPage = () => {
  const [images, setImages] = useState<ImageData[]>([]);
  const [selectedImage, setSelectedImage] = useState<ImageData | null>(null);
  const [selectedImages, setSelectedImages] = useState<{ [key: string]: boolean }>({});

  const handleAddImage = (image: ImageData) => {
    setImages((prevImages) => [...prevImages, image]);
  };

  const handleSelectImage = (image: ImageData) => {
    setSelectedImage(image);
  };

  // Handles updating the fields in the selected image (title, latitude, longitude, annotation)
  const handleInputChange = (field: keyof ImageData, value: string) => {
    if (selectedImage) {
      const updatedImage = { ...selectedImage, [field]: value };
      setSelectedImage(updatedImage);
      setImages(images.map(img => (img.uri === selectedImage.uri ? updatedImage : img)));
    }
  };

  const handleSelectAll = () => {
    const allSelected = images.reduce((acc, image, index) => {
      acc[index] = true;
      return acc;
    }, {} as { [key: string]: boolean });
    setSelectedImages(allSelected);
  };

  const handleDeselectAll = () => {
    const noneSelected = images.reduce((acc, image, index) => {
      acc[index] = false;
      return acc;
    }, {} as { [key: string]: boolean });
    setSelectedImages(noneSelected);
  };

  const handleDeleteSelected = () => {
    const updatedImages = images.filter((image, index) => !selectedImages[index]);

    if (selectedImage && selectedImages[images.indexOf(selectedImage)]) {
      setSelectedImage(null);
    }

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
            <ImageEditor
              image={selectedImage}
              onTitleChange={(value) => handleInputChange('title', value)}
              onLatitudeChange={(value) => handleInputChange('latitude', value)}
              onLongitudeChange={(value) => handleInputChange('longitude', value)}
              onAnnotationChange={(value) => handleInputChange('annotation', value)}
            />
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
