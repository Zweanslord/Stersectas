# Stersectas
Interstellar civilizations carve their own path through space.

## Development Workspace
Development tools used:
* Spring Tool Suite

### Sass Development
To automatically update the css files when making changes to the sass files during local development, run `mvn spring-boot:run`, which runs the server, and `mvn sass:watch`, which automatically generates css when sass files are changed, at the same time.

For Spring Tool Suite a `Sass watch` launch configuration is available. To allow this to work with a the spring boot application running from the workspace, ensure `Refresh using native hooks or polling` is enabled under `Window > Preferences > General > Workspaces`. Note that there can still be a noticeable delay in the sass file change and the server change, because the application is based on workspace files, which need to be refreshed from the file system after the sass watch has run. To shorten this period, use the `Run application` launch configuration, because then the application listens directly to the file changes instead of the workspace files.
