// SidePanel.tsx
import React from 'react';
import { View, TouchableOpacity, Text, StyleSheet, Image, ScrollView } from 'react-native';

interface ImageData {
  uri: string;
  name: string;
}

interface SidePanelProps {
  images: ImageData[];
  onAddImage: (image: ImageData) => void;
  onSelectImage: (image: ImageData) => void;
}

const SidePanel: React.FC<SidePanelProps> = ({ images, onAddImage, onSelectImage }) => {
  const handleAddImage = () => {
    // Simulate adding a test image
    const testImage = {
      uri: 'https://picsum.photos/200/300', // Example placeholder image URL
      name: 'Test Image'
    };
    onAddImage(testImage);
  };

  return (
    <View style={styles.container}>
      <ScrollView style={styles.imageList}>
        {images.map((image, index) => (
          <TouchableOpacity key={index} style={styles.imageItem} onPress={() => onSelectImage(image)}>
            <Image 
              source={{ uri: image.uri }} 
              style={styles.imageThumbnail}
              onError={(error) => console.log('Failed to load image:', error.nativeEvent.error)}
            />
            <Text>{image.name}</Text>
          </TouchableOpacity>
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
    width: 308,
    height: '100%',
    borderWidth: 3,
    borderColor: '#797979',
    backgroundColor: '#ffffff',
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
    flex: 1, // Allow the list to grow and take up available space
  },
  buttonContainer: {
    width: '100%',
    alignItems: 'center',
  },
  imageItem: {
    marginBottom: 10,
    alignItems: 'center',
  },
  imageThumbnail: {
    width: 100,
    height: 100,
    marginBottom: 5,
    backgroundColor: '#ddd', // Temporary background to indicate space even if the image doesn't load
  },
});

export default SidePanel;
