# EternalViewPager

[ ![Download](https://api.bintray.com/packages/star-zero/maven/eternalviewpager/images/download.svg) ](https://bintray.com/star-zero/maven/eternalviewpager/_latestVersion)

Android ViewPager with infinite/looping scroll

This library is a bit different original Viewpager how to use. You should specify a value based on the first value or the last value.

## Usage

### Layout xml

```xml
<com.star_zero.eternalviewpager.EternalViewPager
	android:id="@+id/viewpager"
	android:layout_width="match_parent"
	android:layout_height="match_parent" />
```

### Adapter

Implement class that inherit `EternalPagerAdapter`

```java
public class SimpleAdapter extends EternalPagerAdapter<Integer> {

    private static final int START = 0;
    private static final int END = 10;

    public SimpleAdapter(FragmentManager fragmentManager, Integer initialKey) {
        // Pass the FragmentManager and the initial value.
        super(fragmentManager, initialKey);
    }

    @NonNull
    @Override
    public Fragment getItem(Integer key) {
        // Create and return Fragment.
        return SimpleFragment.createInstance(key);
    }

    @Nullable
    @Override
    protected Integer getNextKey(@NonNull Integer last) {
        // Return the next value.
        // If return null, stop scrolling.
        if (last == END) {
            return null;
        }
        return last + 1;
    }

    @Nullable
    @Override
    protected Integer getPrevKey(@NonNull Integer first) {
        // Return the previous value.
        // If return null, stop scrolling.
        if (first == START) {
            return null;
        }
        return first - 1;
    }

    @Nullable
    @Override
    protected Bundle saveKeysState(@NonNull ArrayList<Integer> keys) {
        // Save current state.
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("keys", keys);
        return bundle;
    }

    @Nullable
    @Override
    protected List<Integer> restoreKeysState(@NonNull Bundle bundle) {
        // Restore state.
        return bundle.getIntegerArrayList("keys");
    }
}
```

At `getNextKey` or `getPrevKey`, you should return the next or previous value of the method parameter. If you return null, stop scrolling.

### Activity/Fragment

```java
SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(), 0);
EternalViewPager viewpager = findViewById(R.id.viewpager);
viewpager.setAdapter(adapter);
```

For more usage, see sample.

## Download

latest version: [ ![Download](https://api.bintray.com/packages/star-zero/maven/eternalviewpager/images/download.svg) ](https://bintray.com/star-zero/maven/eternalviewpager/_latestVersion)

```groovy
implementation 'com.star_zero:eternalviewpager:<latest_version>'
```

## Known Issues

* When scrolling continuously, it looks like reached last data. Then it can scroll again after a stop a moment. Because this library rearranges internal data when scrolling state is stopped.
* Using with TabLayout does not work fine. It's strange behavior when scrolling.

## License

	Copyright 2018 Kenji Abe
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
