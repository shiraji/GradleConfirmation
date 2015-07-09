# Gradle Confirmation

This plugin shows a confirmation dialog before executing gradle tasks.

## Installation

Use the IDE's plugin manager to install the latest version of the plugin.

or

[Download](https://github.com/shiraji/GradleConfirmation/blob/master/GradleConfirmation.jar?raw=true) jar file and then go to Preferences > Plugins > Install plugin from disk... > Select the jar file you downloaded

## Usage

Run Gradle task[s] from Gradle projects window, this plugin popup confirmation dialog asking the developer really want to run the task. If the developer click NO, the plugin stop executing the gradle task[s].

To disable this plugin, go to `Tools > uncheck Show Gradle Confirmation`

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Make sure you put gradle.jar to 'IntelliJ Plugin SDK'. `File > Project Structure... > SDKs > IntelliJ Plugin SDK > click + sign > Pick gradle.jar file from under IntelliJ IDEA's plugin directory`
4. Commit your changes: `git commit -am 'Add some feature'`
5. Push to the branch: `git push origin my-new-feature`
6. Submit a pull request


## History

### ver 0.0.2

Changed how to store settings (PersistentStateComponent to PropertiesComponent)

### ver 0.0.1

nitial release

## License

```
The MIT License (MIT)

Copyright (c) 2015 Yoshinori Isogai

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
