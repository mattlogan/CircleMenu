CircleMenu
==========

Circle's Android application is **really** slick.  I've implemented a slide-down menu similar to the one found in
their app.

All of the display, animation, and click logic is contained in the CircleMenu class.  Just give it some strings and
a listener.

The CircleMenu class contains a ListView child and a corresponding ArrayAdapter, so the menu resizes to fit your needs.
It also contains its own listener interface which simplifies the click data for the user (i.e. an Activity or Fragment).

One drawback to using a View as a reusable UI component instead of a fragment is that it can't save its state (in this case, the currently displayed photo), so you'll have to override onSaveInstanceState() in your Activity or Fragment,
as I've done in this demo app.

To get the full visual effect, it's best to use a transparent or semi-transparent action bar, or no action bar at all.
In this demo app, there's no action bar -- just a small control in the top left of the screen.

![alt tag](https://raw.github.com/mattlogan/CircleMenu/master/github-assets/skateboarder.png)

## How to Use

Add a `CircleMenu` to your layout:

```xml
<com.matthewlogan.circlemenu.library.CircleMenu
        android:id="@+id/circle_menu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/circle_menu_background" />
```

To toggle the menu:

```java
circleMenu.toggle();
```

Implement the `CircleMenu.OnItemClickListener` to get notified of item clicks:

```java
circleMenu.setOnItemClickListener(this);
````

Respond to clicks in `onItemClick()`.

```java
@Override
    public void onItemClick(int position) {
        // do cool stuff here
    }
```

## Customization

You can change the menu background color with the `background` attribute.

Additionally, you can use the `textColor`, `textSize`, and `dividerColor` to further customize your menu:

```xml
<com.matthewlogan.circlemenu.library.CircleMenu
        android:id="@+id/circle_menu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/circle_menu_background"
        app:textSize="18sp"
        app:textColor="@color/white"
        app:dividerColor="@color/white" />
```

## Sample

See the [sample](https://github.com/mattlogan/CircleMenu/tree/master/sample) for a common use of this library.

