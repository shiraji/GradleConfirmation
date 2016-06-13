# Gradle Confirmation

[![Join the chat at https://gitter.im/shiraji/GradleConfirmation](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/shiraji/GradleConfirmation?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-GradleConfirmation-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2350) [![Software License](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](https://github.com/shiraji/GradleConfirmation/blob/master/LICENSE)

This plugin shows a confirmation dialog before executing gradle tasks.

## How to disable confirmation specific gradle task?

In some projects, running tests, showing help, or building project may not affect anything.

## Installation

Use the IDE's plugin manager to install the latest version of the plugin.

or

[Download](https://github.com/shiraji/GradleConfirmation/blob/master/GradleConfirmation.jar?raw=true) jar file and then go to Preferences > Plugins > Install plugin from disk... > Select the jar file you downloaded

## Usage

Run Gradle task[s] from Gradle projects window, this plugin popup confirmation dialog asking the developer really want to run the task. If the developer click NO, the plugin stop executing the gradle task[s].

To disable this plugin, go to `Tools > uncheck Show Gradle Confirmation`

## History

See [CHANGELOG](https://github.com/shiraji/GradleConfirmation/blob/master/CHANGELOG.md)

## License

```
Copyright 2016 Yoshinori Isogai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
