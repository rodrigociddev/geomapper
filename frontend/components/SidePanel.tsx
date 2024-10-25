import React, { useState } from 'react';
import { View, TouchableOpacity, Text, StyleSheet, Image, ScrollView } from 'react-native';
import CheckBox from '@react-native-community/checkbox';

const demoImages = [
  require('../assets/DemoImages/1.jpg'),
  require('../assets/DemoImages/2.jpg'),
  require('../assets/DemoImages/3.jpg'),
  require('../assets/DemoImages/4.jpg'),
  require('../assets/DemoImages/5.jpg'),
  //add more images as needed
];

interface ImageData {
  uri: string;
  name: string;
}

interface SidePanelProps {
  images: ImageData[];
  selectedImages: { [key: string]: boolean };
  setSelectedImages: (selectedImages: { [key: string]: boolean }) => void;
  onAddImage: (image: ImageData) => void;
  onSelectImage: (image: ImageData) => void;
}

const SidePanel: React.FC<SidePanelProps> = ({ images, selectedImages, setSelectedImages, onAddImage, onSelectImage }) => {
  const [lastRandomIndex, setLastRandomIndex] = useState<number | null>(null);

  // Function to randomly pick an image from demoImages
  const handleAddImage = () => {
    let randomIndex;
    
    // Keep picking a new random index until it's different from the last one
    do {
      randomIndex = Math.floor(Math.random() * demoImages.length);
    } while (randomIndex === lastRandomIndex && demoImages.length > 1);

    // Store the new random index as the last selected one
    setLastRandomIndex(randomIndex);

    const randomImage = demoImages[randomIndex];

    // Create a new image object and add it
    const newImage = {
      uri: Image.resolveAssetSource(randomImage).uri, // Resolve asset URI
      name: `Demo Image ${randomIndex + 1}`,
    };
    console.log('Adding random image:', newImage);
    onAddImage(newImage); // Add the randomly selected image
  };

  const toggleImageSelection = (index: number) => {
    setSelectedImages((prevSelected) => ({
      ...prevSelected,
      [index]: !prevSelected[index], // Toggle the selected state based on index
    }));
  };

  return (
    <View style={styles.container}>
      <ScrollView style={styles.imageList}>
        {images.map((image, index) => (
          <View key={index} style={styles.imageItem}>
            <CheckBox
              value={selectedImages[index] || false}
              onValueChange={() => toggleImageSelection(index)}
              style={styles.checkbox}
            />
            <TouchableOpacity onPress={() => onSelectImage(image)} style={styles.imageTouchable}>
              <Image
                source={{ uri: image.uri }}
                style={styles.imageThumbnail}
                onError={(error) => console.log('Failed to load image:', error.nativeEvent.error)}
              />
              <Text style={styles.imageText}>{image.name}</Text>
            </TouchableOpacity>
          </View>
        ))}
      </ScrollView>

      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.addButton} onPress={handleAddImage}>
          <Text style={styles.addButtonText}>+ Add Images</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#070C20',
    width: 308,
    height: '100%',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 10,
  },
  addButton: {
    backgroundColor: '#001F5A',
    padding: 15,
    borderRadius: 10,
    marginBottom: 10,
  },
  addButtonText: {
    color: '#FFFFFF',
    fontSize: 16,
  },
  imageList: {
    width: '100%',
    padding: 10,
    flex: 1,
  },
  buttonContainer: {
    width: '100%',
    alignItems: 'center',
  },
  imageItem: {
    marginBottom: 40,
    width: '100%',
    alignItems: 'center',
    position: 'relative',
  },
  imageTouchable: {
    width: 100,
    height: 100,
  },
  imageThumbnail: {
    width: 100,
    height: 100,
    backgroundColor: '#ddd',
  },
  checkbox: {
    position: 'absolute',
    top: 40,
    right: 40,
  },
  imageText: {
    marginTop: 2,
    textAlign: 'center',
    color: '#FFFFFF',
  },
});

export default SidePanel;
