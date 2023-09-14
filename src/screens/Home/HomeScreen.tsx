// React
import React from 'react';

// Props

// Components

// RN Components
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  View,
  Text,
  Button,
} from 'react-native';

// Styles
import {styled} from 'nativewind';
const StyledView = styled(View);
const StyledScrollView = styled(ScrollView);
const StyledStatus = styled(StatusBar);

// State
import {useStore} from '../../common/app.state';
import {observer} from 'mobx-react-lite';

const HomeScreen = observer(({navigation}): JSX.Element => {
  const {user, count, increment, decrement} = useStore();

  return (
    <SafeAreaView className="text-black dark:text-white">
      <StyledStatus className="text-black dark:text-white" />
      <StyledScrollView
        contentInsetAdjustmentBehavior="automatic"
        className="text-black dark:text-white bg-white/100">
        <StyledView className="text-black dark:text-white bg-white/100">
          <Text>Home</Text>
          <Text>{user.isAuthenticated}</Text>
          <Text>{count}</Text>
        </StyledView>
        <Button title="Increment" onPress={increment} />
        <Button title="Decrement" onPress={decrement} />
        <Button title="Home" onPress={() => navigation.navigate('Home')} />
        <Button title="About" onPress={() => navigation.navigate('About')} />
        <Button title="Survey" onPress={() => navigation.navigate('Survey')} />
        <Button title="Testing" onPress={() => navigation.navigate('Test')} />
      </StyledScrollView>
    </SafeAreaView>
  );
});

export default HomeScreen;
