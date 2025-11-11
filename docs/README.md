# Documentation

This directory contains the comprehensive AsciiDoc documentation for the Spring vs. Quarkus AI Demo project.

## 📄 Files

- **index.adoc** - Main project documentation (AsciiDoc format)

## 🔨 Building Documentation Locally

### Prerequisites

- Ruby 3.x
- Gem/Bundler

### Install AsciiDoctor

```bash
gem install asciidoctor
gem install asciidoctor-pdf
gem install rouge
gem install asciidoctor-diagram
```

### Generate HTML

```bash
asciidoctor -r asciidoctor-diagram \
  -a icons=font \
  -a source-highlighter=rouge \
  -a toc=left \
  docs/index.adoc
```

Output: `docs/index.html`

### Generate PDF

```bash
asciidoctor-pdf -r asciidoctor-diagram \
  -a icons=font \
  docs/index.adoc
```

Output: `docs/index.pdf`

### Validate Syntax

```bash
asciidoctor --dry-run docs/index.adoc
```

## 🌐 Published Documentation

The documentation is automatically published to GitHub Pages when changes are pushed to the main branch.

**URL**: https://JohannesRabauer.github.io/spring-vs-quarkus-ai/

### GitHub Actions Workflows

Two workflows handle documentation:

1. **publish-docs.yml** - Publishes to GitHub Pages on every push to main
2. **build-docs.yml** - Validates and builds on pull requests

## 📝 Documentation Structure

The main documentation (`index.adoc`) includes:

- **Overview** - Project goals and technologies
- **Architecture** - System design and component details
- **Getting Started** - Installation and setup instructions
- **Project Structure** - Directory layout and organization
- **Backend Implementation** - Services, database, and APIs
- **Frontend Implementation** - Vaadin UI details
- **Deployment** - Docker and containerization
- **Configuration** - Environment variables and settings
- **Development** - Building and testing locally
- **Performance Comparison** - Spring vs. Quarkus metrics
- **Troubleshooting** - Common issues and solutions
- **Performance Tuning** - Optimization techniques
- **Security** - Production hardening checklist
- **Integration Guide** - Adding new services and tools
- **Monitoring & Logging** - Observability setup
- **References** - Links and resources
- **Appendix** - Quick command reference

## ✏️ Editing Documentation

To add or modify documentation:

1. Edit `docs/index.adoc` using any text editor
2. Validate changes: `asciidoctor --dry-run docs/index.adoc`
3. Build locally: `asciidoctor docs/index.adoc`
4. Commit and push to main
5. GitHub Actions will automatically publish to GitHub Pages

### AsciiDoc Resources

- [AsciiDoc Syntax Quick Reference](https://docs.asciidoctor.org/asciidoc/latest/syntax-quick-reference/)
- [AsciiDoctor Documentation](https://docs.asciidoctor.org/)
- [Writing AsciiDoc](https://docs.asciidoctor.org/asciidoc/latest/)

## 🚀 Continuous Integration

The CI/CD pipeline automatically:

1. Checks out the repository
2. Validates AsciiDoc syntax
3. Generates HTML and PDF outputs
4. Uploads artifacts
5. Publishes to GitHub Pages

**Trigger events:**
- Pushes to main branch (publish to pages)
- Pull requests with documentation changes (build test)
- Manual workflow dispatch

## 📊 Documentation Formats

### HTML
- Professional styling
- Table of contents navigation
- Syntax highlighting
- Responsive design
- Published to GitHub Pages

### PDF
- Print-friendly format
- Same content as HTML
- Available as artifact in build

## 🔗 Links

- **Repository**: https://github.com/JohannesRabauer/spring-vs-quarkus-ai
- **GitHub Pages**: https://JohannesRabauer.github.io/spring-vs-quarkus-ai/
- **Main README**: ../README.md

## 📞 Support

For documentation issues or improvements:

1. Create a GitHub Issue
2. Submit a Pull Request
3. Contact the maintainer

---

**Last Updated**: November 2024
