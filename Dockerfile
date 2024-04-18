FROM ubuntu

ENV SHA256SUM=63512d132fd80cae9d280541fec8033665dd98a9a8d2e4ff89764e4ea1987554

RUN apt-get update && apt-get install -y curl libusb-1.0-0

# Get the binary file from target url
RUN curl -o energi.tgz https://s3-us-west-2.amazonaws.com/download.energi.software/releases/energi3/v1.1.7/energi3-v1.1.7-linux-amd64.tgz

# Get the checksum of downloaded file, concat the output and store to file
RUN sha256sum energi.tgz | awk '{print $1}' > checksum

RUN cat checksum

# Check the integrity of the downloaded file to env SHA256SUM
RUN echo "Checking file integrity.." \
    if grep checksum | grep -q "$SHA256SUM"; \
        echo "File checksum matched."

RUN tar -xvzf energi.tgz

# Copy the binary file to root workdir
RUN cp energi3-v1.1.7-linux-amd64/bin/* .

CMD ["./energi3"]