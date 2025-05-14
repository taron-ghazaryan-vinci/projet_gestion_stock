import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';

platformBrowserDynamic().bootstrapModule(AppModule)  // Utilise bootstrapModule au lieu de bootstrapApplication
  .catch((err) => console.error(err));
