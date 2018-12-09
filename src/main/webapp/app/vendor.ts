/* after changing this file run 'npm run webpack:build' */
/* tslint:disable */
import '../content/css/vendor.css';

// Imports all fontawesome core and solid icons

import {library} from '@fortawesome/fontawesome-svg-core';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {far} from '@fortawesome/free-regular-svg-icons';

// Adds the SVG icon to the library so you can use it in your page
library.add(far);
library.add(fas);

// jhipster-needle-add-element-to-vendor - JHipster will add new menu items here
