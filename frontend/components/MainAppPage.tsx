// MainAppPage.tsx
import React, { useState } from 'react';
import { View, StyleSheet } from 'react-native';
import TopBarGeoTagR from './TopBarGeoTagR';
import SidePanel from './SidePanel';

interface ImageData {
  uri: string;
  name: string;
}

const MainAppPage = () => {
  const [images, setImages] = useState<ImageData[]>([]); // State to hold added images

  const handleAddImage = (image: ImageData) => {
    setImages((prevImages) => [...prevImages, image]); // Add new image to the list
  };

  const handleSelectImage = (image: ImageData) => {
    console.log('Selected Image:', image); // Log selected image (for now)
  };

  return (
    <View style={styles.container}>
      <TopBarGeoTagR />
      <View style={styles.body}>
        <SidePanel images={images} onAddImage={handleAddImage} onSelectImage={handleSelectImage} />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  body: {
    flexDirection: 'row',
    flex: 1,
  },
});

export default MainAppPage;
