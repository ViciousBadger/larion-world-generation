{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    utils.url = "github:numtide/flake-utils";
  };
  outputs = {
    self,
    nixpkgs,
    utils,
  }: let
    system = "x86_64-linux";
    pkgs = import nixpkgs {inherit system;};
  in
    utils.lib.eachDefaultSystem (system: {
      formatter = pkgs.alejandra;

      devShells.default = pkgs.mkShell rec {
        buildInputs = with pkgs; let
          javaVersion = jdk21;
        in [
          libGL
          (gradle_8.override
            {
              java = javaVersion;
              javaToolchains = [];
            })
          (jdt-language-server.override {jdk = javaVersion;})
        ];
        LD_LIBRARY_PATH = "${nixpkgs.lib.makeLibraryPath buildInputs}";
      };
    });
}
