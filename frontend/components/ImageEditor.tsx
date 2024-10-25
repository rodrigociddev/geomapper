import React from 'react';
import { View, TextInput, Image, ScrollView, StyleSheet } from 'react-native';

interface ImageEditorProps {
  image: {
    uri: string;
    name: string;
    title?: string;
    latitude?: string;
    longitude?: string;
    annotation?: string;
  };
  onTitleChange: (value: string) => void;
  onLatitudeChange: (value: string) => void;
  onLongitudeChange: (value: string) => void;
  onAnnotationChange: (value: string) => void;
}

const ImageEditor: React.FC<ImageEditorProps> = ({
  image,
  onTitleChange,
  onLatitudeChange,
  onLongitudeChange,
  onAnnotationChange,
}) => {
  return (
    <View style={styles.container}>
      <View style={styles.titleRow}>
        <TextInput
          style={styles.titleInput}
          placeholder="Enter Title..."
          value={image.title || ''}
          onChangeText={onTitleChange}
        />
        <TextInput
          style={styles.longLat}
          placeholder="Lat."
          value={image.latitude || ''}
          onChangeText={onLatitudeChange}
        />
        <TextInput
          style={styles.longLat}
          placeholder="Long."
          value={image.longitude || ''}
          onChangeText={onLongitudeChange}
        />
      </View>

      <View style={styles.imageContainer}>
        <Image
          source={{ uri: image.uri }}
          style={styles.image}
          resizeMode="cover"
          resizeMethod="scale"
        />
      </View>

      <ScrollView style={styles.annotationContainer}>
        <TextInput
          style={styles.annotationInput}
          placeholder="Add your annotations here..."
          value={image.annotation || ''}
          onChangeText={onAnnotationChange}
          multiline
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
  titleRow: {
    flexDirection: 'row',
  },
  titleInput: {
    width: '55%',
    padding: 10,
    fontSize: 18,
    borderBottomWidth: 1,
    borderColor: '#ccc',
    backgroundColor: '#C5C5C5',
    marginBottom: 20,
    marginRight: 10,
    opacity: 0.7,
  },
  longLat: {
    width: '10%',
    padding: 10,
    fontSize: 18,
    borderBottomWidth: 1,
    borderColor: '#242424',
    backgroundColor: '#C5C5C5',
    marginBottom: 20,
    marginRight:9,
    opacity: 0.7,
  },
  imageContainer: {
    width: 650,
    height: 400,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 20,
    backgroundColor: '#f0f0f0',
    overflow: 'hidden',
    borderRadius: 2,
  },
  image: {
    width: '100%',
    height: '100%',
    borderRadius: 10,
  },
  annotationContainer: {
    width: '80%',
    padding: 10,
    flex: 1,
    backgroundColor: '#C5C5C5',
    borderRadius: 2,
    borderWidth: 1,
    borderColor: '#ccc',
    opacity: 0.7,
  },
  annotationInput: {
    fontSize: 16,
    width: '114%',
    height: '900%',
    textAlignVertical: 'top',
  },
});

export default ImageEditor;
